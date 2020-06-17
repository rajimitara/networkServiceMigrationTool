package com.swisscom.networkServiceMigrationTool.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Parameters{

    @Column(name="paramA1")
    private String paramA1;

    @Column(name="paramA2")
    private String paramA2;

    public Parameters(){

    }
    Parameters( String param1, String param2){
        this.paramA1 = param1;
        this.paramA2 = param2;
    }


    public String getParamA1() {
        return paramA1;
    }

    public void setParamA1(String paramA1) {
        this.paramA1 = paramA1;
    }

    public String getParamA2() {
        return paramA2;
    }

    public void setParamA2(String paramA2) {
        this.paramA2 = paramA2;
    }
}
