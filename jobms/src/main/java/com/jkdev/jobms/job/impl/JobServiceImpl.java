package com.jkdev.jobms.job.impl;


import com.jkdev.jobms.job.Job;
import com.jkdev.jobms.job.JobRepository;
import com.jkdev.jobms.job.JobService;
import com.jkdev.jobms.job.dto.JobWithCompanyDTO;
import com.jkdev.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JobServiceImpl implements JobService {
    //private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        // RestTemplate communicate with company ms using API and map response to similar external Company class created in jobms
        List<Job> jobs = jobRepository.findAll();
        RestTemplate restTemplate = new RestTemplate();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        for (Job job : jobs) {
            JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job); // set job obj to dto obj
            Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
            jobWithCompanyDTO.setCompany(company);  // get company obj and set to dto obj
            jobWithCompanyDTOS.add(jobWithCompanyDTO);
        }
        return jobWithCompanyDTOS;
    }

    @Override
    public void addJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
