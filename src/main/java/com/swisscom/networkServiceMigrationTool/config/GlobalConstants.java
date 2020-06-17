package com.swisscom.networkServiceMigrationTool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Default class file used for accessing application properties by the batch
 * processor
 */
@Component
public class GlobalConstants {

    @Value("${spring.batch.device.config.inputDir}")
    private String deviceConfigDir;

    @Value("${spring.batch.device.service.outputDir}")
    private String networkServicesConfigDir;


    public String getNetworkServicesConfigDir() {
        return networkServicesConfigDir;
    }

    public void setNetworkServicesConfigDir(String networkServicesConfigDir) {
        this.networkServicesConfigDir = networkServicesConfigDir;
    }

    public String getDeviceConfigDir() {
        return deviceConfigDir;
    }

    public void setDeviceConfigDir(String deviceConfigDir) {
        this.deviceConfigDir = deviceConfigDir;
    }
}
