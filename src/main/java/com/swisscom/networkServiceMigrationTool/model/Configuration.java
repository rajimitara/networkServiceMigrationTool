package com.swisscom.networkServiceMigrationTool.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Configuration{

    @Column(name="configB1")
    private String configB1;

    @Column(name="configB2")
    private String configB2;

    public Configuration(){
    }

    Configuration( String param1, String param2){
        this.configB1 = param1;
        this.configB1 = param2;
    }

    public String getConfigB1() {
        return configB1;
    }

    public void setConfigB1(String configB1) {
        this.configB1 = configB1;
    }

    public String getConfigB2() {
        return configB2;
    }

    public void setConfigB2(String configB2) {
        this.configB2 = configB2;
    }

}
