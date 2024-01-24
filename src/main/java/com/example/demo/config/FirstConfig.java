package com.example.demo.config;

import jakarta.persistence.EntityManagerFactory;
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

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory1",
        basePackages = {
                "com.example.demo.repositories"
        }
)

public class FirstConfig {
        @Primary
        @Bean(name = "dataSource1")
        @ConfigurationProperties(prefix = "spring.db1.datasource")
        public DataSource dataSource(){
                return DataSourceBuilder.create().build();
        }

        @Primary
        @Bean(name = "entityManagerFactory1")
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(
                EntityManagerFactoryBuilder builder, @Qualifier("dataSource1") DataSource dataSource) {
                HashMap<String, Object> properties = new HashMap<>();
                properties.put("hibernate.hbm2ddl.auto", "update");

                return builder.dataSource(dataSource)
                        .properties(properties)
                        .packages("com.example.demo.models")
                        .persistenceUnit("db1")
                        .build();
        }

        @Primary
        @Bean(name = "transactionManager1")
        public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory1")
                                                             EntityManagerFactory entityManagerFactory) {
                return new JpaTransactionManager(entityManagerFactory);
        }
}
