package com.swisscom.networkServiceMigrationTool.drools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import com.swisscom.networkServiceMigrationTool.model.ShortlistedDeviceModels;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


public class DeviceModelIdentifierMatchAnalyser {

    private static final String DEFAULT_MODEL = "DEFAULT_MODEL";
    public static Map<String,DeviceModelMetrics> deviceAttributesMap;


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
                System.out.println("eachIdentiferFromDevice: ["+eachIdentiferFromDevice+"]");

                if(StringUtils.containsIgnoreCase(deviceIdentifier, eachIdentiferFromDevice)){

                    System.out.println("if(StringUtils.containsIgnoreCase: "+eachIdentiferFromDevice);
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
        System.out.println("deviceModel param1 = "+ deviceModel.getParam1() + " param2 = "+ deviceModel.getParam2()  + " param3 = "+deviceModel.getParam3() );
        List<DeviceIdentifierMatchAnalysisResults> deviceIdentifierMatchAnalysisResults = pullAllEligibleDeviceMetrics(deviceIdentifier);
        System.out.println("pullAllEligibleDeviceMetrics(deviceModel): "+ deviceIdentifierMatchAnalysisResults);
        System.out.println("pullAllShortlistedDeviceModels: ");
        ShortlistedDeviceModels ShortlistedDeviceModels = new ShortlistedDeviceModels(pullAllShortListedWorkflowNames(deviceIdentifierMatchAnalysisResults));
        return ShortlistedDeviceModels;
    }

    /**
     * @param keywordMatchResultSet
     * @return
     */
    public HashSet<String> pullAllShortListedWorkflowNames(List<DeviceIdentifierMatchAnalysisResults> keywordMatchResultSet){

        HashSet<String> shortListedDeviceModels = new HashSet<String>();

        for(DeviceIdentifierMatchAnalysisResults eachKeyWordMatchResult : keywordMatchResultSet){
            shortListedDeviceModels.add(eachKeyWordMatchResult.getMatchedDeviceModel());
        }

        System.out.println("shortListedDeviceModels size: "+shortListedDeviceModels.size());

        return shortListedDeviceModels;

    }



    /**
     * @param shortListedWorkflows
     * @param requestedWorkflow
     * @return
     */
    public ShortlistedDeviceModels validateIfServiceExists(HashSet<String> shortListedWorkflows, String requestedWorkflow){

        boolean salienceRequestMatched = false;
        ShortlistedDeviceModels finalisedWorkflowsModel = new ShortlistedDeviceModels();

        System.out.println("CollectionUtils.isNotEmpty(shortListedWorkflows): "+CollectionUtils.isNotEmpty(shortListedWorkflows));

        if(CollectionUtils.isNotEmpty(shortListedWorkflows)){
            System.out.println("1shortListedWorkflows: "+shortListedWorkflows.size());

            if(shortListedWorkflows.contains(requestedWorkflow))
                salienceRequestMatched = true;
        }

        finalisedWorkflowsModel.setSalienceRequestMatched(salienceRequestMatched);
        return finalisedWorkflowsModel;
    }

    /**
     * @param deviceIdentifiers
     * @return
     */
    public String iterateAndIdentifyKeywordMatch(String deviceIdentifiers){

        initialiseDeviceModelAttributes();

        String matchedWorkflowByKeyWord = "";

        System.out.println("deviceIdentifiers: "+deviceIdentifiers);

        for (Map.Entry<String,DeviceModelMetrics> eachDeviceMapEntry : deviceAttributesMap.entrySet()) {
            String deviceName = eachDeviceMapEntry.getKey();
            DeviceModelMetrics eachDeviceMetrics = eachDeviceMapEntry.getValue();

            List<String> setOfIdentifiers = eachDeviceMetrics.getIdentifierKeywords();

            for(String eachIdentifierFromDevice : setOfIdentifiers){
                System.out.println("eachIdentifierFromDevice:"+eachIdentifierFromDevice);
                if(StringUtils.containsIgnoreCase(deviceIdentifiers, eachIdentifierFromDevice)){ //deviceId ,uuid match

                    System.out.println("if(StringUtils.containsIgnoreCase");
                    matchedWorkflowByKeyWord = deviceName;
                    return matchedWorkflowByKeyWord;
                }
            }

        }


        return matchedWorkflowByKeyWord;
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

        System.out.println("initialiseWorkflowAttributes() triggered");

        deviceAttributesMap = new HashMap<String,DeviceModelMetrics>();

        deviceAttributesMap.put("MODEL_ONE",new DeviceModelMetrics("MODEL_ONE",deviceModelIdentifier));
        deviceAttributesMap.put("MODEL_TWO",new DeviceModelMetrics("MODEL_TWO",deviceModelIdentifier2));
        deviceAttributesMap.put("MODEL_THREE",new DeviceModelMetrics("MODEL_THREE",deviceModelIdentifier3));
        deviceAttributesMap.put("MODEL_FOUR",new DeviceModelMetrics("MODEL_FOUR",deviceModelIdentifier4));


    }


}
