package com.swisscom.networkServiceMigrationTool.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Conf{

    @Column(name="configC1")
    private String configC1;

    @Column(name="configC2")
    private String configC2;

    public Conf(){

    }

    Conf( String param1, String param2){
        this.configC1 = param1;
        this.configC2 = param2;
    }

    public String getConfigC1() {
        return configC1;
    }

    public void setConfigC1(String configC1) {
        this.configC1 = configC1;
    }

    public String getConfigC2() {
        return configC2;
    }

    public void setConfigC2(String configC2) {
        this.configC2 = configC2;
    }

}
