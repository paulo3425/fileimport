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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("productJob")
public class JobRunController {

    @Autowired
    private JobLauncher jobLauncher;

    @Qualifier("productJob")
    @Autowired
    private Job productJob;

    @RequestMapping("/file/{input_file_name}/execute")
    @ResponseBody
    public String requestJob3(@PathVariable("input_file_name") String inputFileName) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        try {
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("INPUT_FILE_PATH", inputFileName);

            jobLauncher.run(productJob, jobParametersBuilder.toJobParameters());
            return "Job " + inputFileName + " has executed!";

        } catch (JobInstanceAlreadyCompleteException ex) {
            return new String("This job has been completed already!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
