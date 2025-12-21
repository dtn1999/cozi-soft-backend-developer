package com.cozi.soft.listing;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class ServiceModuleTestConfig {

    @Bean
    public DbResetService dbResetService(JdbcTemplate jdbcTemplate) {
        return new DbResetService(jdbcTemplate);
    }

}
