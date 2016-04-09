package com.mimiczo.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by mimiczo on 2015.12.24
 */
@Component
@ConfigurationProperties(locations="classpath:application.yml", prefix="security")
@Data
public class SecurityProperties {

    private String jwt_base;
}
