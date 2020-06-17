package com.swisscom.networkServiceMigrationTool.drools;

import java.util.List;

public class DeviceModelMetrics {

    private String deviceName;
    private List<String> identifierKeywords;

    public DeviceModelMetrics(String deviceName, List<String> identifierKeywords) {
        super();
        this.deviceName = deviceName;
        this.identifierKeywords = identifierKeywords;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<String> getIdentifierKeywords() {
        return identifierKeywords;
    }

    public void setIdentifierKeywords(List<String> identifierKeywords) {
        this.identifierKeywords = identifierKeywords;
    }


}
