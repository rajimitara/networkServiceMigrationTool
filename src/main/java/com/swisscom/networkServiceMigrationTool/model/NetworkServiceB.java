package com.swisscom.networkServiceMigrationTool.model;

import javax.persistence.*;

@Entity
@Table(name = "NETWORK_SERVICE_B")
@Access(value = AccessType.FIELD)
public class NetworkServiceB extends NetworkService {

    /*
    network_service_type: "B"
device: "id12"
configuration:
  configB2: "value12_2"
     */
    public static final String TYPE = "B";
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "gen", strategy = "foreign", parameters = { @Parameter(name = "property", value = "employee") })
    private long id;

    @Column(name = "device")
    private String device;

    @Column(name = "NETWORK_SERVICE_TYPE")
    private String networkServiceType;

    @Column(name = "configuration")
    private Configuration configuration;

    public NetworkServiceB() {
        this.networkServiceType = TYPE;
    }

    public String getDevice() {
        return device;
    }
    public void setDevice(String device) {
        this.device = device;
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

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String deviceModelServiceType() {
        return networkServiceType;
    }

    @Override
    public String getFileNameExtension() {
        return "_serviceB";
    }

    @Override
    public Integer getPriorityService() {
        return 1;
    }

    public String getDeviceModel() {
        return device;
    }


}
