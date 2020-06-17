package com.swisscom.networkServiceMigrationTool.web.rest;

import com.swisscom.networkServiceMigrationTool.config.Constants;
import com.swisscom.networkServiceMigrationTool.config.GlobalConstants;
import com.swisscom.networkServiceMigrationTool.model.IncomingBatchRequest;
import com.swisscom.networkServiceMigrationTool.model.JobResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;



/* Migration Tool back-end <--> Tool GUI -> the back-end will expose various APIs to trigger actions
and provide data back for representation purposes */



@RestController
@RequestMapping("/job")
public class JobResource {
    @Autowired
    private JobLauncher jobLauncher;

    private static final Logger logger = LoggerFactory.getLogger(JobResource.class);

    @Autowired
    @Qualifier(value="simpleNetworkServiceMapper")
    private Job job;

    @Autowired
    private GlobalConstants globalConstants;


    @PostMapping("/migrateDevices")
    public ResponseEntity<JobResponse> downloadDeviceConfiguration(@RequestBody @Valid IncomingBatchRequest incomingBatchRequest) {

        Map<String, JobParameter> parameterMap = new HashMap<>();

        if(StringUtils.isNotBlank(incomingBatchRequest.networkServicesSolutionDirectory) && StringUtils.isNotBlank(incomingBatchRequest.networkServicesSolutionDirectory)) {


            String deviceConfigLocation = incomingBatchRequest.deviceConfigurationDirectory + incomingBatchRequest.fileName;
            deviceConfigLocation = StringUtils.isEmpty(deviceConfigLocation) ? globalConstants.getDeviceConfigDir() : deviceConfigLocation;
            parameterMap.put(Constants.JOB_PARAM_FILE_NAME, new JobParameter(deviceConfigLocation));
            globalConstants.setDeviceConfigDir(deviceConfigLocation);

            try {
                JobExecution execution = jobLauncher.run(job, new JobParameters(parameterMap));
                logger.info("JobId {}, JobStatus {}", execution.getJobId(), execution.getStatus().getBatchStatus());
                JobResponse jobResponse = new JobResponse(execution.getJobId(), new Date(), execution.getStatus().getBatchStatus(), "Job triggered successfully");
                return new ResponseEntity<>(jobResponse, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(new JobResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new JobResponse("Please specify input and output dir for legacy device analysis"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}


