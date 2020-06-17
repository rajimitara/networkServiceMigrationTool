package com.swisscom.networkServiceMigrationTool.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DEVICE_MODEL")
@Access(value= AccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceModel implements Serializable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty
    @Column(name="DEVICE_ID")
    private String deviceId;

    @JsonProperty
    @Column(name="DEVICE")
    private String uuid;

    @JsonProperty
    @Column(name="PARAM1")
    private String param1;

    @JsonProperty
    @Column(name="PARAM2")
    private String param2;

    @JsonProperty
    @Column(name = "PARAM3")
    private String param3;

    private String fileName;

    public DeviceModel(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
