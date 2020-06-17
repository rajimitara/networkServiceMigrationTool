package com.swisscom.networkServiceMigrationTool.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsBeanConfiguration {

    private static final String kratosShortListModelsdrl = "MAPPING_DEVICE_TO_SERVICE_RULES.drl";

    /**
     *
     * Bean for Shortlisting
     *
     * @return
     */
    @Bean
    public KieContainer kieShortListNSContainer() {

        return buildKIEModuleforDRL(kratosShortListModelsdrl);

    }


    /**
     * @return
     */


    /**
     *
     * TODO: Write-up for method
     *
     * @param drlFileName
     * @return
     */
    private KieContainer buildKIEModuleforDRL(String drlFileName){
        KieServices kieServices = KieServices.Factory.get();

        System.out.println("Entering the kieContainer() method");

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFileName));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        System.out.println("kieModule is built for DRL: ["+drlFileName+"]");

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

}