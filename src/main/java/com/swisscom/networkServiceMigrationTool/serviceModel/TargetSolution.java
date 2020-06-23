package com.swisscom.networkServiceMigrationTool.serviceModel;

import com.swisscom.networkServiceMigrationTool.drools.SelectedDevices;
import com.swisscom.networkServiceMigrationTool.model.NetworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TargetSolution {


    @Autowired
    private ServiceModel serviceModel;

    private static final Logger logger = LoggerFactory.getLogger(TargetSolution.class);

    public Map<String, NetworkService> mapServiceModelAndDataModel(List<NetworkService> serviceModels, List<? extends SelectedDevices> selectedDevices, Map<String, NetworkService> migrateServicesInCiscoNetwork){
        List<NetworkService> targetSolution = new ArrayList<>();
        int i=0;
        for(SelectedDevices selectedDevice : selectedDevices){
            NetworkService targetNetworkService = serviceModels.get(i);
            List<NetworkService> possibleNetworkServices = selectedDevice.getSelectedDevices();

            NetworkService bestNSInServiceModel = classifyBestSuitableNS(targetNetworkService,possibleNetworkServices);

            targetSolution.add(bestNSInServiceModel);
            migrateServicesInCiscoNetwork.put(targetNetworkService.getDeviceModel(),bestNSInServiceModel);
            logger.info("DeviceId {}, ServiceModelType {} , DataModelType {}  ",selectedDevice.getFileName(),targetNetworkService.deviceModelServiceType(),bestNSInServiceModel.deviceModelServiceType());
        }

        return migrateServicesInCiscoNetwork;
    }

    private NetworkService classifyBestSuitableNS(NetworkService targetNetworkService, List<NetworkService> possibleNetworkServices) {
        //hierarchy NSA < NSB < NSC , expressed through priority parameter in NetworkService
        int noOFPossibilities = possibleNetworkServices.size();
        if(noOFPossibilities == 1){
            return possibleNetworkServices.get(0);
        }else {
            //else respect hierarchy and pick best possible.
            Collections.sort(possibleNetworkServices, new Comparator<NetworkService>() {
                @Override
                public int compare(NetworkService networkService1, NetworkService networkService2) {
                    return networkService2.getPriorityService().compareTo(networkService2.getPriorityService());
                }
            });

            return possibleNetworkServices.get(noOFPossibilities - 1);
        }
    }
}
