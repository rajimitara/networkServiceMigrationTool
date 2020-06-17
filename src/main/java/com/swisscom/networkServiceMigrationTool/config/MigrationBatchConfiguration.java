/*
package com.swisscom.networkServiceMigrationTool.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
//@EnableBatchProcessing
public class MigrationBatchConfiguration extends SimpleBatchConfiguration {

    private JobRepository jobRepository; //persists metada about batch in DB
    private JobExplorer jobExplorer; //retrieve information from Db/job repository
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager batchTransactionManager;

    @Autowired
    @Qualifier(value = "batchDataSource")
    private DataSource batchDataSource;


    public JobRepository getJobRepository() throws Exception {
        return this.jobRepository;
    }


    public JobLauncher getJobLauncher() throws Exception {
        return this.jobLauncher;
    }


    public JobExplorer getJobExplorer() throws Exception {
        return this.jobExplorer;
    }


    public PlatformTransactionManager getTransactionManager() {
        return this.batchTransactionManager;
    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(this.batchDataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }


    private JobExplorer createJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(this.batchDataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return  jobExplorerFactoryBean.getObject();
    }

    //verify lifecycle of it
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        this.jobRepository = createJobRepository();
        this.jobExplorer = createJobExplorer();
        this.jobLauncher = createJobLauncher();
    }

}
*/
