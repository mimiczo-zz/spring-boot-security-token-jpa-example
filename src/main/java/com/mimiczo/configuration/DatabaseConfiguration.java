package com.mimiczo.configuration;

import com.mimiczo.configuration.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.mimiczo.configuration.properties.JpaProperties;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.mimiczo.repository"})
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Autowired
	private DatabaseProperties databaseProperties;
	@Autowired
	private JpaProperties jpaProperties;

	@Bean(name="dataSource", destroyMethod = "shutdown")
    @Primary
    public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
        config.setJdbcUrl(databaseProperties.getJdbcUrl());
        config.addDataSourceProperty("dataSourceClassName", databaseProperties.getDataSourceClassName());
        config.addDataSourceProperty("user", databaseProperties.getUsername());
        config.addDataSourceProperty("password", databaseProperties.getPassword());
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "120");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "1024");
		config.addDataSourceProperty("useServerPrepStmts", "true");
		config.addDataSourceProperty("connectionCollation", "utf8mb4_general_ci");
    	config.setIdleTimeout(300000);//5분
    	config.setMaxLifetime(600000);//10분
    	config.setConnectionTestQuery("select 1");
    	config.setConnectionTimeout(databaseProperties.getConnectionTimeout());//30초
    	config.setValidationTimeout(databaseProperties.getValidationTimeout());//5초
    	config.setMinimumIdle(databaseProperties.getMinIdle());
    	config.setMaximumPoolSize(databaseProperties.getMaxIdle());
		return new HikariDataSource(config);
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setPackagesToScan("com.mimiczo.domain");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties properties = new Properties();
		properties.put(AvailableSettings.DIALECT, jpaProperties.getDialect());
		properties.put(AvailableSettings.HBM2DDL_AUTO, jpaProperties.getDdl_auto());
		properties.put(AvailableSettings.SHOW_SQL, jpaProperties.isShow_sql());
		properties.put(AvailableSettings.FORMAT_SQL, jpaProperties.isFormat_sql());
		properties.put(AvailableSettings.USE_SQL_COMMENTS, jpaProperties.isUse_sql_comments());
		properties.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, jpaProperties.isNew_generator_mappings());
		properties.put("hibernate.ejb.naming_strategy", jpaProperties.getNaming_strategy());
		factory.setJpaProperties(properties);

		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
}