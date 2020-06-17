package com.swisscom.networkServiceMigrationTool.config;

import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.utils.DeviceScraper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class CustomItemReader extends AbstractItemCountingItemStreamItemReader<DeviceModel> {

    @Autowired
    GlobalConstants globalConstants;

    @Value("#{jobParameters['" + Constants.JOB_PARAM_INPUT_FILE_NAME + "']}")
    private String deviceConfigDir;

    private IteratorItemReader<DeviceModel> deviceModelList;

    @Override
    protected DeviceModel doRead() {
        return deviceModelList.read();
    }

    @Override
    protected void doOpen( ) throws Exception {

        setName(CustomItemReader.class.getName());
        List<DeviceModel> deviceModelList = new ArrayList<>();
        String deviceConfigInputDir = StringUtils.isNotBlank(getDeviceConfigDir()) ? getDeviceConfigDir() : globalConstants.getDeviceConfigDir();
        if (StringUtils.contains(deviceConfigInputDir, ".txt")) {
            deviceModelList.addAll(DeviceScraper.scrapeTextFile(deviceConfigInputDir));
        } else if (StringUtils.contains(deviceConfigInputDir, ".json")) {
            deviceModelList.addAll(DeviceScraper.scrapeJsonFile(deviceConfigInputDir));
        }else{
            deviceModelList.addAll(DeviceScraper.scrapeTextFile(deviceConfigInputDir));
            deviceModelList.addAll(DeviceScraper.scrapeJsonFile(deviceConfigInputDir));
        }
        this.deviceModelList = new IteratorItemReader<DeviceModel>(deviceModelList);
    }

    @Override
    protected void doClose() {

    }

    public String getDeviceConfigDir() {
        return deviceConfigDir;
    }

    public void setDeviceConfigDir(String deviceConfigDir) {
        this.deviceConfigDir = deviceConfigDir;
    }
}
