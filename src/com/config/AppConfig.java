package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// To enable JPA 
@EnableJpaRepositories
@EnableTransactionManagement
@Configuration  // States that this is Config source -- same as spring.xml
//Package to be scanned with any exception given below
@ComponentScan(basePackages = "com.*")

public class AppConfig {

}