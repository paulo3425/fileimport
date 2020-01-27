package com.fileimport.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productJob")
public class JobRunController {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("productJob")
    @Autowired
    private Job productJob;


    @PostMapping("/file/{input_file_name}/execute")
    public ResponseEntity<Void> requestJob3(@PathVariable("input_file_name") String inputFileName) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("INPUT_FILE_PATH", inputFileName);

            jobLauncher.run(productJob, jobParametersBuilder.toJobParameters());
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (JobInstanceAlreadyCompleteException ex) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
