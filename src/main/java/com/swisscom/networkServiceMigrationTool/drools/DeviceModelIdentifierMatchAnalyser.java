package com.swisscom.networkServiceMigrationTool.drools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.model.ShortlistedDeviceModels;
import com.swisscom.networkServiceMigrationTool.serviceModel.TargetSolution;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeviceModelIdentifierMatchAnalyser {

    private static final String DEFAULT_MODEL = "DEFAULT_MODEL";
    public static Map<String,DeviceModelMetrics> deviceAttributesMap;

    private static final Logger logger = LoggerFactory.getLogger(DeviceModelIdentifierMatchAnalyser.class);

    /**
     * @param deviceIdentifier
     * @return
     */
    public List<DeviceIdentifierMatchAnalysisResults> pullAllEligibleDeviceMetrics(String deviceIdentifier){
        List<DeviceIdentifierMatchAnalysisResults> keywordMatchResultSet = new ArrayList<DeviceIdentifierMatchAnalysisResults>();

        initialiseDeviceModelAttributes();

        for (Map.Entry<String,DeviceModelMetrics> eachDeviceModelMapEntry : deviceAttributesMap.entrySet()) {
            String deviceModelName = eachDeviceModelMapEntry.getKey();
            DeviceModelMetrics eachDeviceModelMetrics = eachDeviceModelMapEntry.getValue();

            List<String> setOfIdentifiers = eachDeviceModelMetrics.getIdentifierKeywords(); //deviceID ->M1 // uuid => M2

            for(String eachIdentiferFromDevice : setOfIdentifiers){
                logger.info("eachIdentiferFromDevice: ["+eachIdentiferFromDevice+"]");

                if(StringUtils.containsIgnoreCase(deviceIdentifier, eachIdentiferFromDevice)){

                    logger.info("if(StringUtils.containsIgnoreCase: "+eachIdentiferFromDevice);
                    List<String> matchedIdentifier = new ArrayList<String>();
                    matchedIdentifier.add(eachIdentiferFromDevice);

                    DeviceIdentifierMatchAnalysisResults identifierMatchAnalysisResults = new DeviceIdentifierMatchAnalysisResults();
                    identifierMatchAnalysisResults.setMatchedDeviceModel(deviceModelName);
                    identifierMatchAnalysisResults.setMatchFound(true);
                    identifierMatchAnalysisResults.setMatchedIdentifier(matchedIdentifier);

                    keywordMatchResultSet.add(identifierMatchAnalysisResults);
                }
            }

        }

        return keywordMatchResultSet;
    }

    /**
     * @param deviceModel
     * @return
     */
    public ShortlistedDeviceModels analyseIdentifierMatchCriteria(DeviceModel deviceModel){

        //TODO : pull in  all Identifier fields of a device into a String.
        String deviceIdentifier = !StringUtils.isBlank(deviceModel.getDeviceId()) ? "deviceId" : "uuid";
        logger.info("deviceModel param1 = "+ deviceModel.getParam1() + " param2 = "+ deviceModel.getParam2()  + " param3 = "+deviceModel.getParam3() );
        List<DeviceIdentifierMatchAnalysisResults> deviceIdentifierMatchAnalysisResults = pullAllEligibleDeviceMetrics(deviceIdentifier);
        logger.info("pullAllEligibleDeviceMetrics(deviceModel): "+ deviceIdentifierMatchAnalysisResults);
        logger.info("pullAllShortlistedDeviceModels: ");
        ShortlistedDeviceModels ShortlistedDeviceModels = new ShortlistedDeviceModels(pullAllShortListedDeviceModelNames(deviceIdentifierMatchAnalysisResults));
        return ShortlistedDeviceModels;
    }

    /**
     * @param keywordMatchResultSet
     * @return
     */
    public HashSet<String> pullAllShortListedDeviceModelNames(List<DeviceIdentifierMatchAnalysisResults> keywordMatchResultSet){

        HashSet<String> shortListedDeviceModels = new HashSet<String>();

        for(DeviceIdentifierMatchAnalysisResults eachKeyWordMatchResult : keywordMatchResultSet){
            shortListedDeviceModels.add(eachKeyWordMatchResult.getMatchedDeviceModel());
        }

        logger.info("shortListedDeviceModels size: "+shortListedDeviceModels.size());

        return shortListedDeviceModels;

    }



    /**
     * @param shortListedDevice
     * @param requestedDeviceModel
     * @return
     */
    public ShortlistedDeviceModels validateIfServiceExists(HashSet<String> shortListedDevice, String requestedDeviceModel){

        boolean salienceRequestMatched = false;
        ShortlistedDeviceModels finalisedDeviceModel = new ShortlistedDeviceModels();

        logger.info("CollectionUtils.isNotEmpty(shortListedDevice): "+CollectionUtils.isNotEmpty(shortListedDevice));

        if(CollectionUtils.isNotEmpty(shortListedDevice)){
            logger.info("1shortListedDevice: "+shortListedDevice.size());

            if(shortListedDevice.contains(requestedDeviceModel))
                salienceRequestMatched = true;
        }

        finalisedDeviceModel.setSalienceRequestMatched(salienceRequestMatched);
        return finalisedDeviceModel;
    }

    /**
     * @param deviceIdentifiers
     * @return
     */
    public String iterateAndIdentifyKeywordMatch(String deviceIdentifiers){

        initialiseDeviceModelAttributes();

        String matchedDeviceModelByKeyWord = "";

        logger.info("deviceIdentifiers: "+deviceIdentifiers);

        for (Map.Entry<String,DeviceModelMetrics> eachDeviceMapEntry : deviceAttributesMap.entrySet()) {
            String deviceName = eachDeviceMapEntry.getKey();
            DeviceModelMetrics eachDeviceMetrics = eachDeviceMapEntry.getValue();

            List<String> setOfIdentifiers = eachDeviceMetrics.getIdentifierKeywords();

            for(String eachIdentifierFromDevice : setOfIdentifiers){
                logger.info("eachIdentifierFromDevice:"+eachIdentifierFromDevice);
                if(StringUtils.containsIgnoreCase(deviceIdentifiers, eachIdentifierFromDevice)){ //deviceId ,uuid match

                    logger.info("if(StringUtils.containsIgnoreCase");
                    matchedDeviceModelByKeyWord = deviceName;
                    return matchedDeviceModelByKeyWord;
                }
            }

        }


        return matchedDeviceModelByKeyWord;
    }

    //TODO : initialise from DB.
    private void initialiseDeviceModelAttributes(){
        List<String> deviceModelIdentifier = new ArrayList<String>();

        deviceModelIdentifier.add("deviceId");

        List<String> deviceModelIdentifier2 = new ArrayList<String>();

        deviceModelIdentifier2.add("uuid");

        List<String> deviceModelIdentifier3 = new ArrayList<String>();

        deviceModelIdentifier3.add("kid");

        List<String> deviceModelIdentifier4 = new ArrayList<String>();

        deviceModelIdentifier4.add("fid");

        logger.info("initialiseDeviceAttributes() triggered");

        deviceAttributesMap = new HashMap<String,DeviceModelMetrics>();

        deviceAttributesMap.put("MODEL_ONE",new DeviceModelMetrics("MODEL_ONE",deviceModelIdentifier));
        deviceAttributesMap.put("MODEL_TWO",new DeviceModelMetrics("MODEL_TWO",deviceModelIdentifier2));
        deviceAttributesMap.put("MODEL_THREE",new DeviceModelMetrics("MODEL_THREE",deviceModelIdentifier3));
        deviceAttributesMap.put("MODEL_FOUR",new DeviceModelMetrics("MODEL_FOUR",deviceModelIdentifier4));


    }


}
