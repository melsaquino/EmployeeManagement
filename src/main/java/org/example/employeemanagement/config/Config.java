package org.example.employeemanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "url")
public class Config {
    public static final String HOME_URL = "/employees";
    public static final String LOGIN_URL = "/login";

}
