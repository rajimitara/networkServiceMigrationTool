package com.swisscom.networkServiceMigrationTool.drools;

import java.util.List;

public class DeviceIdentifierMatchAnalysisResults {

    private boolean matchFound;
    private List<String> matchedIdentifier;
    private String matchedDeviceModel;

    public DeviceIdentifierMatchAnalysisResults() {
        super();
    }

    public DeviceIdentifierMatchAnalysisResults(boolean matchFound, List<String> matchedKeywords, String matchedDeviceModel) {
        super();
        this.matchFound = matchFound;
        this.matchedIdentifier = matchedKeywords;
        this.matchedDeviceModel = matchedDeviceModel;
    }


    public boolean isMatchFound() {
        return matchFound;
    }
    public void setMatchFound(boolean matchFound) {
        this.matchFound = matchFound;
    }
    public List<String> getMatchedIdentifier() {
        return matchedIdentifier;
    }
    public void setMatchedIdentifier(List<String> matchedIdentifier) {
        this.matchedIdentifier = matchedIdentifier;
    }
    public String getMatchedDeviceModel() {
        return matchedDeviceModel;
    }
    public void setMatchedDeviceModel(String matchedDeviceModel) {
        this.matchedDeviceModel = matchedDeviceModel;
    }



}