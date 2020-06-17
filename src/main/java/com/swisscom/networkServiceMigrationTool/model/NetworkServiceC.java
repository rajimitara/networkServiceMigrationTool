package com.swisscom.networkServiceMigrationTool.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkServiceC extends NetworkService {


    /* network_service_type: "C"
        dev: "id22"
        conf:
          configC1: "value22_1"
          configC2: "value22_3"*/
    public static final String TYPE = "C";
    @JsonProperty
    private String dev;

    @JsonProperty
    private String configC1;
    @JsonProperty
    private String configC2;

    @Column(name="NETWORK_SERVICE_TYPE")
    private String networkServiceType;

    @Embedded
    private Conf conf;

    public NetworkServiceC(){
        this.networkServiceType = TYPE;
    }

    public NetworkServiceC(String dev, String networkServiceType, String configC1, String configC2) {
        this.dev = dev;
        this.networkServiceType = networkServiceType;
        this.configC1 = configC1;
        this.configC2 = configC2;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(String networkServiceType) {
        this.networkServiceType = networkServiceType;
    }


    public Conf getConf() {
        return conf;
    }

    public void setConf(Conf conf) {
        this.conf = conf;
    }

    @Override
    public Class exhibitNaturalBehaviour() {
        return this.getClass();
    }
    @Override
    public String deviceModelServiceType(){
        return networkServiceType;
    }

    @Override
    public String getFileNameExtension() {
        return "_serviceC";
    }

    @Override
    public Integer getPriorityService() {
        return 2;
    }

    @Override
    public String getDeviceModel() {
        return dev;
    }
}
