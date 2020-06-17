package com.swisscom.networkServiceMigrationTool.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DeviceScraper {


    public static List<DeviceModel> scrapeCsvFile(String csvFilePath) {
        List<DeviceModel> deviceModels = new ArrayList<>();
        for (File file : finder(csvFilePath, ".csv")) {
            DeviceModel deviceModel = new DeviceModel();
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                            .withHeader("deviceId", "param1", "param2", "param3")
                            .withIgnoreHeaderCase()
                            .withTrim());
            ) {
                for (CSVRecord csvRecord : csvParser) {
                    // Accessing values by the names assigned to each column
                    deviceModel.setDeviceId(csvRecord.get("deviceId"));
                    deviceModel.setUuid(csvRecord.get("uuid"));
                    deviceModel.setParam1(csvRecord.get("param1"));
                    deviceModel.setParam2(("param2"));
                    deviceModel.setParam3(("param3"));

                }
                deviceModels.add(deviceModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deviceModels;
    }

    public static File[] finder(String dirName, String endsWith) {
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(endsWith);
            }
        });

    }

    public static List<DeviceModel> scrapeTextFile(String txtFilePath) throws IOException {
        List<DeviceModel> deviceModels = new ArrayList<>();
        List<File> file = Arrays.asList(new File(txtFilePath));
        File[] textFiles = StringUtils.contains(txtFilePath,".txt") ? file.toArray(new File[0]) : finder(txtFilePath, ".txt");

        for (File textFile : textFiles) {
            String fileName = String.valueOf(textFile);
            Stream<String> lines = Files.lines(Paths.get(fileName));
            Map<String, String> resultMap =
                    lines.map(line -> line.split("="))
                            .collect(Collectors.toMap(line -> line[0], line -> line[1]));
            lines.close();
            final ObjectMapper mapper = new ObjectMapper();
            DeviceModel deviceModel = mapper.convertValue(resultMap, DeviceModel.class);
            deviceModel.setFileName((textFile.getName().replaceAll(".txt","")));
            deviceModels.add(deviceModel);
        }
        return deviceModels;
    }

    public static List<DeviceModel> scrapeJsonFile(String jsonFilePath) throws IOException {
        List<DeviceModel> deviceModels = new ArrayList<>();
        for (File file : finder(jsonFilePath, ".json")) {
            ObjectMapper mapper = new ObjectMapper();
            // Java object from JSON file
            DeviceModel deviceModel = mapper.readValue(file, DeviceModel.class);
            deviceModel.setFileName((file.getName().replaceAll(".json","")));
            deviceModels.add(deviceModel);
        }
        return deviceModels;
    }

}
