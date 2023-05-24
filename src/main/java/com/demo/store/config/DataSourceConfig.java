package com.demo.store.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@Configuration
public class DataSourceConfig {

    @Autowired
    private DataConfig dataConfig;

    /**
     * Default data source.
     *
     * @return the data source
     */
    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource defaultDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(dataConfig.getDataSourceDriver());
        dataSource.setJdbcUrl(dataConfig.getDataSourceUrl());
        dataSource.setUsername(dataConfig.getDataSourceUsername());
        dataSource.setPassword(dataConfig.getDataSourcePassword());
        dataSource.setMaximumPoolSize(dataConfig.getMaximumPoolSize());
        dataSource.setConnectionTimeout(dataConfig.getConnectionTimeout());
        dataSource.setMinimumIdle(dataConfig.getMinimumIdle());
        dataSource.setIdleTimeout(dataConfig.getIdleTimeout());
        return dataSource;
    }

    /**
     * Default entity manager.
     *
     * @return the entity manager
     */
    @Bean(name = "defaultEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean defaultEntityManager(
            JpaVendorAdapter jpaVendorAdapter) {
        // Entity manager factory
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(defaultDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("com.demo.store.entity");
        entityManager.setPersistenceUnitName("defaultPersistenceUnit");
        entityManager.setJpaPropertyMap(getHibernateProperties());
        return entityManager;
    }

    /**
     * Default transaction manager.
     *
     * @return the transaction manager
     */
    @Primary
    @Bean(name = "defaultTransactionManager")
    public PlatformTransactionManager defaultTransactionManager(JpaVendorAdapter jpaVendorAdapter) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                defaultEntityManager(jpaVendorAdapter).getObject());

        return transactionManager;
    }

    /**
     * Get hibernate properties
     *
     * @return the map properties
     */
    public Map<String, Object> getHibernateProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AvailableSettings.SHOW_SQL, Boolean.toString(dataConfig.isJpaShowSql()));
        properties.put(AvailableSettings.HBM2DDL_AUTO, dataConfig.getJpaDdlAuto());
        properties.put(AvailableSettings.DIALECT, dataConfig.getJpaDialect());
        properties.put(AvailableSettings.NON_CONTEXTUAL_LOB_CREATION, dataConfig.getJpaLob());
        return properties;
    }
}
