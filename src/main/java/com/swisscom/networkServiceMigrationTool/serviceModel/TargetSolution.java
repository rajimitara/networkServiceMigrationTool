package com.swisscom.networkServiceMigrationTool.serviceModel;

import com.swisscom.networkServiceMigrationTool.drools.SelectedDevices;
import com.swisscom.networkServiceMigrationTool.model.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TargetSolution {


    @Autowired
    private ServiceModel serviceModel;

    public Map<String, NetworkService> mapServiceModelAndDataModel(List<NetworkService> serviceModels, List<? extends SelectedDevices> selectedDevices, Map<String, NetworkService> migrateServicesInCiscoNetwork){
        List<NetworkService> targetSolution = new ArrayList<>();
        int i=0;
        for(SelectedDevices selectedDevice : selectedDevices){
            NetworkService targetNetworkService = serviceModels.get(i);
            List<NetworkService> possibleNetworkServices = selectedDevice.getSelectedDevices();

            NetworkService bestNSInServiceModel = classifyBestSuitableNS(targetNetworkService,possibleNetworkServices);

            targetSolution.add(bestNSInServiceModel);
            migrateServicesInCiscoNetwork.put(targetNetworkService.getDeviceModel(),bestNSInServiceModel);

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
