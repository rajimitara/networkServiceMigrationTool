package com.swisscom.networkServiceMigrationTool;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.cloud.task.configuration.EnableTask;

//@EnableTask
@EnableBatchProcessing
@ImportResource("classpath:spring.xml")
@SpringBootApplication
public class NetworkServiceMigrationToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkServiceMigrationToolApplication.class, args);
	}

}
