package com.swisscom.networkServiceMigrationTool.config;

import com.swisscom.networkServiceMigrationTool.drools.NetworkServiceSuggestionService;
import com.swisscom.networkServiceMigrationTool.drools.SelectedDevices;
import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.serviceModel.ServiceModel;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CustomItemProcessor implements ItemProcessor<DeviceModel, SelectedDevices> {

    @Autowired
    private NetworkServiceSuggestionService networkServiceSuggestionService;

    @Override
    public SelectedDevices process(DeviceModel deviceModel) {
        SelectedDevices selectedDevices = new SelectedDevices();
        selectedDevices.setFileName(deviceModel.getFileName());
        return networkServiceSuggestionService.suggestNetworkServices(deviceModel, selectedDevices);
    }
}