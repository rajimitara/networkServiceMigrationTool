package com.swisscom.networkServiceMigrationTool.drools;

import com.swisscom.networkServiceMigrationTool.model.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectedDevices {

    private List<NetworkService> selectedDevices;

    private String fileName;

    public SelectedDevices() {

    }

    public SelectedDevices(List<NetworkService> selectedDevices) {
        super();
        this.selectedDevices = selectedDevices;
    }

    public List<NetworkService> getSelectedDevices() {
        return selectedDevices;
    }

    public void setSelectedDevices(List<NetworkService> selectedDevices) {
        this.selectedDevices = selectedDevices;
    }

    public void addToSelectedDevices(NetworkService selectedDevice) {
        if(selectedDevices == null){
            this.selectedDevices = new ArrayList<>();
        }
        this.selectedDevices.add(selectedDevice);
    }

    public String getNetworkServiceTypes () {
        return  selectedDevices.stream().map(NetworkService::deviceModelServiceType).collect(Collectors.joining(","));
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}