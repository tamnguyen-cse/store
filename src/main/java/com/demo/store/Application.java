package com.demo.store;

import jakarta.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
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
