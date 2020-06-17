package com.swisscom.networkServiceMigrationTool.serviceModel;

import com.swisscom.networkServiceMigrationTool.drools.SelectedDevices;
import com.swisscom.networkServiceMigrationTool.model.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ServiceModel {

    //D1 - NS_Type (choose 1)
    //D2 - NS_Type
    //... so on

    private List<NetworkService> networkServices;

    //Depending on product and technology, this service model will be initialised. But we have auto populated randomly.
    public List<NetworkService> pickServiceModel(List<? extends SelectedDevices> selectedDevices){
        Random random = new Random();
        List<NetworkService> networkServiceList = new ArrayList<>();
        for(SelectedDevices selectedDevice : selectedDevices) {
            int size = selectedDevice.getSelectedDevices().size() ;
            int val = random.nextInt(size);
            networkServiceList.add(selectedDevice.getSelectedDevices().get(val));
        }
        return networkServiceList;
    }

    public List<NetworkService> getNetworkServices() {
        return networkServices;
    }

    public void setNetworkServices(List<NetworkService> networkServices) {
        this.networkServices = networkServices;
    }
}
