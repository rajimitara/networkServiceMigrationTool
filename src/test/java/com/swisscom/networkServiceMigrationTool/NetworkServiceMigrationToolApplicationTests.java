
package com.swisscom.networkServiceMigrationTool;

import com.swisscom.networkServiceMigrationTool.config.Constants;
import com.swisscom.networkServiceMigrationTool.config.CustomItemReader;
import com.swisscom.networkServiceMigrationTool.model.DeviceModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.function.Function;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.item.database.JpaItemWriter;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkServiceMigrationToolApplication.class)
@ActiveProfiles("dev")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        StepScopeTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@Transactional
public class NetworkServiceMigrationToolApplicationTests {

@Test
	public void contextLoads() {
	}


    @Autowired
    private Job job;

    @Autowired
    private CustomItemReader reader;

    /*@Autowired
    private Function<DeviceModel, PatientEntity> processor;

    @Autowired
    private JpaItemWriter<PatientEntity> writer;

    @Autowired
    private PatientRepository patientRepository;*/

    private JobParameters jobParameters;

    @Before
    public void setUp() {
        Map<String, JobParameter> params = new HashMap<>();
        String parameter =  System.getProperty("user.dir") + File.separator + "data" + File.separator + "test-unit-testing.txt";
        params.put(Constants.JOB_PARAM_INPUT_FILE_NAME, new JobParameter(parameter));
        jobParameters = new JobParameters(params);
    }

    @Test
    public void test() {
        assertNotNull(job);
        assertEquals(Constants.JOB_NAME, job.getName());
    }

    @Test
    public void testReader() {
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution(jobParameters);
        int count = 0;
        try {
            count = StepScopeTestUtils.doInStepScope(stepExecution, () -> {
                int numDeviceModel = 0;
                DeviceModel deviceModel;
                try {
                    reader.open(stepExecution.getExecutionContext());
                    while ((deviceModel = reader.read()) != null) {
                        assertNotNull(deviceModel);
                        //assertEquals("uuid21", deviceModel.getUuid());
                        assertEquals("value21_1", deviceModel.getParam1());
                        assertEquals("", deviceModel.getParam2());
                        assertEquals("", deviceModel.getParam3());
                        numDeviceModel++;
                    }
                } finally {
                    try { reader.close(); } catch (Exception e) { fail(e.toString()); }
                }
                return numDeviceModel;
            });
        } catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(1, count);
    }

}
