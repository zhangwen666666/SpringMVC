package com.zw.ssm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.zw.ssm.service")
@PropertySource("classpath:jdbc.properties")
@Import({DataSourceConfig.class, MybatisConfig.class})
@EnableTransactionManagement
public class SpringConfig {
}
