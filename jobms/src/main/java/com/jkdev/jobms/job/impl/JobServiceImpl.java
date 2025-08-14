package com.jkdev.jobms.job.impl;


import com.jkdev.jobms.job.Job;
import com.jkdev.jobms.job.JobRepository;
import com.jkdev.jobms.job.JobService;
import com.jkdev.jobms.job.clients.CompanyClient;
import com.jkdev.jobms.job.clients.ReviewClient;
import com.jkdev.jobms.job.dto.JobDTO;
import com.jkdev.jobms.job.external.Company;
import com.jkdev.jobms.job.external.Review;
import com.jkdev.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobServiceImpl implements JobService {
    //private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired // tell spring to provide instance of restTemplate on runtime
    RestTemplate restTemplate;

    private CompanyClient CompanyClient; // obj of Company CLient
    private ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient CompanyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.CompanyClient = CompanyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name="companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()); // alternative of for loop
        // this::convertToDTO is equivalent to writing:   job -> this.convertToDTO(job);
        // => for every element (Job object) in the stream, the convertToDTO method will be invoked to transform it
    }
    // companyBreaker - same instance of circuit breaker as in application.properties
    // companyBreakerFallback - method which provides info about company ms status when it is down

    public List<String> companyBreakerFallback(Exception e) {
        List<String> list = new ArrayList<>();
        list.add("Company Service is not available");
        return list;
    }

    // method to convert each job and company obj to combined dto obj
    private JobDTO convertToDTO(Job job) {
        //Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
        // getForObject -> better when one obj is returned by API
        // exchange -> better when api returns list of objs
        Company company = CompanyClient.getCompany(job.getCompanyId());
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                                                HttpMethod.GET,
//                                                null, // since req body is null
//                                                new ParameterizedTypeReference<List<Review>>() { // tells generic type of response, here list of reviews
//                                                });
//        List<Review> reviews = reviewResponse.getBody(); // get body from response
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        JobDTO jobDTO = JobMapper.mapJobWithCompanyDTO(job, company, reviews); // maps all 3 objs and returns a single dto obj
        return jobDTO;
    }

    @Override
    public void addJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
            if(jobOptional.isPresent()) {
                Job job = jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                return true;
            }
        return false;
    }
}
