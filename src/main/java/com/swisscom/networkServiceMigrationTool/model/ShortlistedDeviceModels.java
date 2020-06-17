package com.swisscom.networkServiceMigrationTool.model;

import org.kie.api.definition.type.Modifies;
import org.kie.api.definition.type.PropertyReactive;

import java.util.HashSet;

//@PropertyReactive
public class ShortlistedDeviceModels {

    HashSet<String> shortlistedDeviceModels;
    boolean salienceRequestMatched;

    public ShortlistedDeviceModels() {
    }

    public boolean isSalienceRequestMatched() {
        return salienceRequestMatched;
    }

    public void setSalienceRequestMatched(boolean salienceRequestmatched) {
        this.salienceRequestMatched = salienceRequestmatched;
    }

    public ShortlistedDeviceModels(HashSet<String> shortlistedDeviceModels) {
        super();
        this.shortlistedDeviceModels = shortlistedDeviceModels;
    }

    public HashSet<String> getShortlistedDeviceModels() {
        return shortlistedDeviceModels;
    }

    //@Modifies({"shortlistedDeviceModels"})
    public void setShortlistedDeviceModels(HashSet<String> shortlistedDeviceModels) {
        this.shortlistedDeviceModels = shortlistedDeviceModels;
    }

    public  boolean isShortlistedModels(){
        return true;
    }

}
