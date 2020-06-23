package com.swisscom.networkServiceMigrationTool.drools;

import com.esotericsoftware.yamlbeans.YamlWriter;
import com.swisscom.networkServiceMigrationTool.model.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public
class NetworkServiceSuggestionService {

    @Autowired
    private KieContainer kieShortListNSContainer;

    @Autowired
    private KieContainer kieSalientNSContainer;

    private static final Logger logger = LoggerFactory.getLogger(NetworkServiceSuggestionService.class);

    public NetworkServiceSuggestionService() {
        super();
    }

    public SelectedDevices suggestNetworkServices(DeviceModel deviceModel, SelectedDevices selectedDevices) {

        DeviceModelIdentifierMatchAnalyser deviceModelIdentifierMatchAnalyser = new DeviceModelIdentifierMatchAnalyser();
        ShortlistedDeviceModels shortlistedDeviceModels = new ShortlistedDeviceModels();

        KieSession kieShortListNSSession = createKIESession(kieShortListNSContainer);

        kieShortListNSSession.insert(deviceModel);
        logger.info("Firing kieShortListNSSession rulShortlistedDeviceModels now");

        logger.info("shortListedDeviceModel.getShortlistedDeviceModels(): "+shortlistedDeviceModels.getShortlistedDeviceModels());

        kieShortListNSSession.setGlobal("deviceModelIdentifierMatchAnalyser", deviceModelIdentifierMatchAnalyser);
        kieShortListNSSession.setGlobal("selectedDevices", selectedDevices);
        logger.info("Fired kieShortListNSSession rules now: "+kieShortListNSSession.fireAllRules());
        kieShortListNSSession.dispose();


        return selectedDevices;
    }

    private void buildNetworkServiceYamlFiles(SelectedDevices selectedDevices) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter("output.yaml"));
        List<NetworkService> networkServices = selectedDevices.getSelectedDevices();
        if(networkServices!=null){
            for(NetworkService networkService : networkServices) {
                writer.getConfig().setClassTag("NetworkService", networkService.exhibitNaturalBehaviour());
                writer.write(networkService);
            }}
        writer.close();
    }

    /**
     * @param drlSpecificContainer
     * @return
     */
    private KieSession createKIESession(KieContainer drlSpecificContainer){
        return drlSpecificContainer.newKieSession();
    }

}
