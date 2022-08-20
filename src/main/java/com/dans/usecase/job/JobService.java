package com.dans.usecase.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dans.domain.http.JobHTTP;

@Service
public class JobService {
    @Autowired
    private JobHTTP jobHTTP;

    public ResponseEntity<?> getJobs() {
        return ResponseEntity.ok(jobHTTP.getJobs());
    }

    public ResponseEntity<?> getJobDetail(String jobID) {
        return ResponseEntity.ok(jobHTTP.getJobByID(jobID));
    }
}
