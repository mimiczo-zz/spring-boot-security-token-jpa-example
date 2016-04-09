package com.mimiczo.configuration.filter;

import lombok.extern.slf4j.Slf4j;
import com.mimiczo.configuration.exception.GlobalError;
import com.mimiczo.configuration.exception.GlobalException;
import com.mimiczo.configuration.handler.TokenSimpleUrlAuthenticationSuccessHandler;
import com.mimiczo.configuration.security.JWTAuthenticationToken;
import com.mimiczo.configuration.security.NoOpAuthenticationManager;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SecurityTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	public final String HEADER_SECURITY_TOKEN = "X-CUSTOM-TOKEN";
    
    public SecurityTokenAuthenticationFilter(String defaultFilterProcessesUrl) {
       super(defaultFilterProcessesUrl);
       super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
       setAuthenticationManager(new NoOpAuthenticationManager());
       setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
    }

    /**
     * Attempt to authenticate request - basically just pass over to another method to authenticate request headers
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(HEADER_SECURITY_TOKEN);
        String uri = request.getRequestURI();
        log.info("{}", "Access Token Find : "+token);
        AbstractAuthenticationToken userAuthenticationToken = authUserByToken(response, token, uri);
        if(userAuthenticationToken == null) throw new GlobalException(GlobalError.SEC_E401);
        return userAuthenticationToken;
    }

   /**
    * authenticate the user based on token
    * @return
    */
   private AbstractAuthenticationToken authUserByToken(HttpServletResponse response, String token, String uri) {
       if(StringUtils.isEmpty(token)) return null;

       //Token Authentication
       AbstractAuthenticationToken authToken = new JWTAuthenticationToken(token);

       //Database Authentication
       /**
       User details = (User) userDetailsService.loadUserByUsername(token);
       if(details==null) {
    	   return null;
       }

       log.debug("details found:"+details.toString());
       AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(details, details.getId(), details.getAuthorities());
       try {
           return authToken;
       } catch (Exception e) {
           log.error("Authenticate user by token error: ", e);
       }
        */

       return authToken;
   }

   @Override
   public void doFilter(ServletRequest req, ServletResponse res,
           FilterChain chain) throws IOException, ServletException {
       super.doFilter(req, res, chain);
   }
}