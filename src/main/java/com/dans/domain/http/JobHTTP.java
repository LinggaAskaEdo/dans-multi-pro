package com.dans.domain.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dans.model.entity.Job;

@Service
public class JobHTTP {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.job-list}")
    private String urlJobList;

    @Value("${url.job-detail}")
    private String urlJobDetail;

    public List<Job> getJobs() {
        ResponseEntity<List<Job>> responseEntity = restTemplate.exchange(urlJobList, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Job>>() {
                });

        return responseEntity.getBody();
    }

    public Job getJobByID(String jobID) {
        Map<String, String> values = new HashMap<>();
        values.put("id", jobID);

        String url = StringSubstitutor.replace(urlJobDetail, values, "{", "}");

        ResponseEntity<Job> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Job>() {
                });

        return responseEntity.getBody();
    }
}
