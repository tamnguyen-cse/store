package com.demo.store;

import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@ComponentScan({"com.demo.store"})
@EntityScan("com.demo.store.entity")
@EnableJpaRepositories(basePackages = {
        "com.demo.store.repository"}, entityManagerFactoryRef = "defaultEntityManager", transactionManagerRef = "defaultTransactionManager")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        // It will set UTC time zone
        Locale.setDefault(Locale.UK);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Spring boot application running in Locale :" + Locale.getDefault());
        log.info("Spring boot application running in UTC timezone :" + TimeZone.getDefault());
    }

}
