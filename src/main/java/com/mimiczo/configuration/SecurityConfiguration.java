package com.mimiczo.configuration;

import com.mimiczo.configuration.filter.SecurityTokenAuthenticationFilter;
import com.mimiczo.configuration.security.RestAuthenticationEntryPoint;
import com.mimiczo.domain.system.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public SecurityTokenAuthenticationFilter securityTokenAuthenticationFilter(){
		return new SecurityTokenAuthenticationFilter("/**");
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public RoleHierarchyImpl roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		StringBuilder roleHierarchyValue = new StringBuilder();
		int i = 0;
		int total = Authority.values().length;
		for(Enum<?> role : Authority.values()) {
			roleHierarchyValue.append(role);
			if(i>0 && i+1!=total) roleHierarchyValue.append(" "+ role);
			if(i+1!=total) roleHierarchyValue.append(" > ");
			i++;
		}
		roleHierarchy.setHierarchy(roleHierarchyValue.toString());
		return roleHierarchy;
	}

	private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
		return defaultWebSecurityExpressionHandler;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/"
				, "/favicon.ico"
				, "/html/**"
				, "**.html"
				, "/index**"
				, "/resources/**"
				, "/static/**"
				, "/error**"
		);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.and()
				.authorizeRequests()
					.expressionHandler(webExpressionHandler())
					.antMatchers("/admin/**").hasRole(Authority.ADMIN.name())
					.antMatchers("/corp/**").hasRole(Authority.CORP.name())
					.antMatchers("/user/**").hasRole(Authority.USER.name())
					.anyRequest().permitAll()
				.and()
					.addFilterAfter(securityTokenAuthenticationFilter(), BasicAuthenticationFilter.class)
					.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
				.and()
					.sessionManagement().maximumSessions(1).and().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					;
	}
}
