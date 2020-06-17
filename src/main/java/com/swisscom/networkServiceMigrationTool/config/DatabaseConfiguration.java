package com.swisscom.networkServiceMigrationTool.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@EnableJpaRepositories(
        value = "com.swisscom.networkServiceMigrationTool",
        entityManagerFactoryRef = "entityManagerFactory")
@Configuration
public class DatabaseConfiguration {

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
        config.setUsername(env.getProperty("spring.datasource.username"));
        config.setPassword(env.getProperty("spring.datasource.password"));
        config.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        //config.setMinimumIdle(env.getProperty("spring.datasource.min-idle",
            //    Integer.class, 2));
        //config.setMaximumPoolSize(env.getProperty("spring.datasource.max-active",
          //      Integer.class, 100));
        config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        //config.setRegisterMbeans(true);
        return new HikariDataSource(config);

    }
    @Bean(name = "batchJpaVendorAdapter")
    public JpaVendorAdapter batchJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emfBean =
                new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(batchDataSource());
        emfBean.setPackagesToScan("com.swisscom.networkServiceMigrationTool");
        //emfBean.setBeanName("batchEntityManagerFactory");
        emfBean.setJpaVendorAdapter(batchJpaVendorAdapter());


        Properties jpaProps = new Properties();
        //jpaProps.put("hibernate.physical_naming_strategy",
          //      env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
        //jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty(
          //      "spring.jpa.hibernate.ddl-auto", "none"));
        jpaProps.put("hibernate.jdbc.fetch_size", env.getProperty(
                "spring.jpa.properties.hibernate.jdbc.fetch_size",
                "200"));

        Integer batchSize = env.getProperty(
                "spring.jpa.properties.hibernate.jdbc.batch_size",
                Integer.class, 100);
        if (batchSize > 0) {
            jpaProps.put("hibernate.jdbc.batch_size", batchSize);
            jpaProps.put("hibernate.order_inserts", "true");
            jpaProps.put("hibernate.order_updates", "true");
        }

        jpaProps.put("hibernate.show_sql", env.getProperty(
                "spring.jpa.properties.hibernate.show_sql", "false"));
        jpaProps.put("hibernate.format_sql",env.getProperty(
                "spring.jpa.properties.hibernate.format_sql", "false"));

        emfBean.setJpaProperties(jpaProps);
        return emfBean;
    }

    @ConditionalOnMissingBean
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(batchDataSource());
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }


   /* @Bean
    public SpringLiquibase liquibase(LiquibaseProperties liquibaseProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(batchDataSource());
        //liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            //log.debug("Configuring Liquibase");
        }
        return liquibase;
    }*/
}
