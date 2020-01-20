package com.fresh.dbfinjector.dataAloha;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.fresh.dbfinjector.dataAloha",
        entityManagerFactoryRef = "alohaEntityManagerFactory",
        transactionManagerRef = "alohaTransactionManager"
)
public class AlohaDataConfig {

//    @Primary
    @Bean(name = "alohaDataSource")
    @ConfigurationProperties("aloha.datasource")
    public DataSource alohaDataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Primary
    @Bean(name = "alohaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean alohaEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("alohaDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.fresh.alohainjector.dataAloha")
                .persistenceUnit("aloha")
                .build();
    }

//    @Primary
    @Bean(name = "alohaTransactionManager")
    PlatformTransactionManager alohaTransactionManager(
            @Qualifier("alohaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
