package com.swisscom.networkServiceMigrationTool.config;

import com.esotericsoftware.yamlbeans.YamlWriter;
import com.swisscom.networkServiceMigrationTool.drools.SelectedDevices;
import com.swisscom.networkServiceMigrationTool.model.DeviceAnalyserService;
import com.swisscom.networkServiceMigrationTool.model.NetworkService;
import com.swisscom.networkServiceMigrationTool.serviceModel.ServiceModel;
import com.swisscom.networkServiceMigrationTool.serviceModel.TargetSolution;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@StepScope
public class CustomItemWriter implements ItemWriter<SelectedDevices> {

    @Autowired
    GlobalConstants globalConstants;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Value("#{jobParameters['" + Constants.JOB_PARAM_OUTPUT_DIR + "']}")
    private String networkServiceDir;

    @Autowired
    private ServiceModel serviceModel;

    @Autowired
    private TargetSolution targetSolution;

    @Override
    public void write(List<? extends SelectedDevices> selectedDevices) throws Exception {
        JpaItemWriter<DeviceAnalyserService> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        List<DeviceAnalyserService> deviceAnalyserServiceList = new ArrayList<>();
        String networkServiceDir = StringUtils.isNotBlank(getNetworkServiceDir()) ? getNetworkServiceDir() : globalConstants.getNetworkServicesConfigDir();
        for (SelectedDevices selectedDevice : selectedDevices) {
            List<NetworkService> networkServices = selectedDevice.getSelectedDevices();
            if (networkServices != null) {
                for (NetworkService networkService : networkServices) {
                    String outputFileName = selectedDevice.getFileName() + networkService.getFileNameExtension() + ".yaml";
                    YamlWriter writer = new YamlWriter(new FileWriter(networkServiceDir + File.separator + outputFileName));
                    writer.getConfig().setClassTag("NetworkService", networkService.exhibitNaturalBehaviour());
                    writer.write(networkService);
                    writer.close();
                    DeviceAnalyserService deviceAnalyserService = new DeviceAnalyserService();
                    deviceAnalyserService.setDeviceModels(networkService.deviceModelServiceType());
                    deviceAnalyserServiceList.add(deviceAnalyserService);
                }
            }
        }
        buildOptimisedNetworkSolution(selectedDevices, deviceAnalyserServiceList);
        jpaItemWriter.write(deviceAnalyserServiceList);
    }

    private void buildOptimisedNetworkSolution(List<? extends SelectedDevices> selectedDevices, List<DeviceAnalyserService> deviceAnalyserServiceList) {
        List<NetworkService> serviceModels = serviceModel.pickServiceModel(selectedDevices);
        Map<String,NetworkService> migrateServicesInCiscoNetwork = new LinkedHashMap<>();
        targetSolution.mapServiceModelAndDataModel(serviceModels, selectedDevices,migrateServicesInCiscoNetwork);
        int i=0;
        for(Map.Entry<String,NetworkService> entrySet : migrateServicesInCiscoNetwork.entrySet()) {

            String device = entrySet.getKey();
            NetworkService networkService = entrySet.getValue();
            DeviceAnalyserService deviceAnalyserService = new DeviceAnalyserService();
            deviceAnalyserService.setDeviceId(device);
            deviceAnalyserService.setDeviceId(device);
            deviceAnalyserService.setServiceModel(serviceModels.get(i++).deviceModelServiceType());
            deviceAnalyserService.setDeviceModels(networkService.deviceModelServiceType());
            deviceAnalyserServiceList.add(deviceAnalyserService);
            System.out.println("DeviceID:"+ device + " to Network Service " + networkService.deviceModelServiceType());
        }
    }


    public String getNetworkServiceDir() {
        return networkServiceDir;
    }

    public void setNetworkServiceDir(String networkServiceDir) {
        this.networkServiceDir = networkServiceDir;
    }
}