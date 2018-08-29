package com.fresh.alohainjector.dataFresh;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.fresh.alohainjector.dataFresh.repositories",
        entityManagerFactoryRef = "freshEntityManagerFactory",
        transactionManagerRef = "freshTransactionManager"
)
public class FreshDataConfig {
    @Primary
    @Bean(name = "freshDataSource")
    @ConfigurationProperties("fresh.datasource")
    public DataSource freshDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Primary
    @Bean(name = "freshEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean freshEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("freshDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.fresh.alohainjector.dataFresh.domain")
                .persistenceUnit("fresh")
                .build();
    }
    @Primary
    @Bean(name = "freshTransactionManager")
    PlatformTransactionManager freshTransactionManager(
            @Qualifier("freshEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
