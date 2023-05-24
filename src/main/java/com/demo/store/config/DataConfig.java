package com.demo.store.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class DataConfig {

    /** The data source driver . */
    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriver;

    /** The data source URL. */
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    /** The data source username. */
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    /** The data source password. */
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    /** The JPA show SQL. */
    @Value("${spring.jpa.show-sql}")
    private boolean jpaShowSql;

    /** The JPA DDL auto. */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String jpaDdlAuto;

    /** The JPA dialect. */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String jpaDialect;

    /** The JPA dialect. */
    @Value("${spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation}")
    private String jpaLob;

    /** The data source maximum pool size. */
    @Value("${spring.datasource.maximum.pool.size}")
    private int maximumPoolSize;

    /** The data source connection timeout. */
    @Value("${spring.datasource.connection.timeout}")
    private int connectionTimeout;

    /** The data source minimum idle. */
    @Value("${spring.datasource.minimum.idle}")
    private int minimumIdle;

    /** The data source idle timeout. */
    @Value("${spring.datasource.idle.timeout}")
    private int idleTimeout;

}
