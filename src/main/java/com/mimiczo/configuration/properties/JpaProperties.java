package com.mimiczo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations="classpath:application.yml", prefix="spring.jpa")
@Data
public class JpaProperties {

	private String ddl_auto;
	private String naming_strategy;
	private String dialect;
	private boolean new_generator_mappings;
	private boolean show_sql;
	private boolean format_sql;
	private boolean use_sql_comments;
}