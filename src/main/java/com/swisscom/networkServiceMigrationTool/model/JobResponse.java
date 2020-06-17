package com.swisscom.networkServiceMigrationTool.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.batch.runtime.BatchStatus;
import java.util.Date;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {

    private Long jobId;
    private Date timeStamp;
    private BatchStatus status;
    private String message;

    public JobResponse(Long jobId, Date timeStamp, BatchStatus status, String message) {
        this.jobId = jobId;
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
    }

    public JobResponse(String message) {
        this.message = message;
    }
}
