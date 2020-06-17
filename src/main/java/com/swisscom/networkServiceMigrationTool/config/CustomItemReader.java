package com.swisscom.networkServiceMigrationTool.config;

import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.utils.DeviceScraper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class CustomItemReader extends AbstractItemCountingItemStreamItemReader<DeviceModel> {

    @Autowired
    GlobalConstants globalConstants;
    private IteratorItemReader<DeviceModel> deviceModelList;

    @Override
    protected DeviceModel doRead() throws Exception {
        return deviceModelList.read();
    }

    @Override
    protected void doOpen() throws Exception {
        setName(CustomItemReader.class.getName());
        List<DeviceModel> deviceModelList = new ArrayList<>();
        deviceModelList.addAll(DeviceScraper.scrapeTextFile(globalConstants.getDeviceConfigDir()));
        deviceModelList.addAll(DeviceScraper.scrapeJsonFile(globalConstants.getDeviceConfigDir()));
        this.deviceModelList = new IteratorItemReader<DeviceModel>(deviceModelList);
    }

    @Override
    protected void doClose() throws Exception {

    }

}
