package com.dans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dans.usecase.job.JobService;

@RestController
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public ResponseEntity<?> getJobs() {
        return jobService.getJobs();
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobDetail(@PathVariable("id") String jobID) {
        return jobService.getJobDetail(jobID);
    }
}
