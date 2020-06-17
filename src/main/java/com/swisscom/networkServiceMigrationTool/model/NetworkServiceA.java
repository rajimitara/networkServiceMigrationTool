package com.swisscom.networkServiceMigrationTool.model;

import javax.persistence.*;

@Entity
@Table(name = "NETWORK_SERVICE_A")
@Access(value= AccessType.FIELD)
public class NetworkServiceA extends NetworkService {

    /*
    network_service_type: "A"
device_id: "id21"
parameters:
  paramA1: "value21_1"
  paramA2: "0"
     */
    public static final String TYPE = "A";

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "gen", strategy = "foreign", parameters = { @Parameter(name = "property", value = "employee") })
    private long id;

    @Column(name="device_id")
    private String deviceId;

    @Column(name="network_service_type")
    private String networkServiceType;

    @Column(name="parameters")
    @Embedded
    private Parameters parameters;

    public NetworkServiceA(){
        this.networkServiceType = TYPE;
    }

    public NetworkServiceA(String deviceId,String networkServiceType, String paramA1, String paramA2) {
       this.deviceId = deviceId;
        this.networkServiceType = networkServiceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(String networkServiceType) {
        this.networkServiceType = networkServiceType;
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
        return "_serviceA";
    }

    @Override
    public Integer getPriorityService() {
        return 0;
    }

    @Override
    public String getDeviceModel() {
        return deviceId;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    /* public String appendToFileName(){
    *       return "_serviceA";
    * }*/

}
