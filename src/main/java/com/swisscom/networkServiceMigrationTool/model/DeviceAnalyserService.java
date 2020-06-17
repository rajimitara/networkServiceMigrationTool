package com.swisscom.networkServiceMigrationTool.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "DATA_MODELS")
@Access(value= AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceAnalyserService {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty("deviceId")
    @Column(name="DEVICE_ID")
    private String deviceId;

    @JsonProperty("serviceModel")
    @Column(name="SERVICE_MODEL")
    private String serviceModel; // expected service model service type

    @JsonProperty("ns_type")
    @Column(name = "NS_TYPE")
    private String deviceModels; // optimised service solution

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceModels() {
        return deviceModels;
    }

    public void setDeviceModels(String deviceModels) {
        this.deviceModels = deviceModels;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServiceModel() {
        return serviceModel;
    }

    public void setServiceModel(String serviceModel) {
        this.serviceModel = serviceModel;
    }
}
