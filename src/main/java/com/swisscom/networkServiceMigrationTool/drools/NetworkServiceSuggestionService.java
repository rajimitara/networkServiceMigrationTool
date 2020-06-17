package com.swisscom.networkServiceMigrationTool.drools;

import com.esotericsoftware.yamlbeans.YamlWriter;
import com.swisscom.networkServiceMigrationTool.model.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
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

    public NetworkServiceSuggestionService() {
        super();
    }

    public SelectedDevices suggestNetworkServices(DeviceModel deviceModel, SelectedDevices selectedDevices) {

        DeviceModelIdentifierMatchAnalyser deviceModelIdentifierMatchAnalyser = new DeviceModelIdentifierMatchAnalyser();
        ShortlistedDeviceModels shortlistedDeviceModels = new ShortlistedDeviceModels();

        KieSession kieShortListNSSession = createKIESession(kieShortListNSContainer);
        //kieShortListNSSession.setGlobal("shortlistedDeviceModels",shortlistedDeviceModels);

        kieShortListNSSession.insert(deviceModel);
        //kieShortListNSSession.setGlobal("deviceModel",deviceModel);
        System.out.println("Firing kieShortListNSSession rulShortlistedDeviceModels now");

        //kieShortListNSSession.dispose();

        System.out.println("shortListedWorkflowModel.getShortlistedWorkflows(): "+shortlistedDeviceModels.getShortlistedDeviceModels());

        //KieSession kieSalientNSSession = createKIESession(kieSalientWFContainer);
        kieShortListNSSession.setGlobal("deviceModelIdentifierMatchAnalyser", deviceModelIdentifierMatchAnalyser);
        kieShortListNSSession.setGlobal("selectedDevices", selectedDevices);

        //kieShortListNSSession.insert(shortListedWorkflowModel);
        //kieShortListNSSession.insert(kieShortListNSSession);
        System.out.println("Fired kieShortListNSSession rules now: "+kieShortListNSSession.fireAllRules());

        //System.out.println("Fired kieShortListNSSession rules now: "+kieShortListNSSession.fireAllRules());
        kieShortListNSSession.dispose();

        //System.out.println("selectedDevices.getNetworkServiceTypes(): "+selectedDevices.getNetworkServiceTypes());
        //DataModels dataModels = new DataModels();
        //dataModels.setDeviceModels(selectedDevices.getNetworkServiceTypes());

        //buildNetworkServiceYamlFiles(selectedDevices);

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
