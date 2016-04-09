package com.mimiczo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations="classpath:application.yml", prefix="spring.datasource")
@Data
public class DatabaseProperties {
	
	private String jdbcUrl;
	private String username;
	private String password;
	private String serverName;
	private String dataSourceClassName;
	private int connectionTimeout;
	private int validationTimeout;
	private int minIdle;
	private int maxIdle;
}