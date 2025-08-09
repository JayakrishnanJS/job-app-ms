package com.jkdev.jobms.job;

import com.jkdev.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void addJob(Job job);

    JobWithCompanyDTO getJobById(Long id);
    boolean deleteJobById(Long id);

    boolean updateJobById(Long id, Job updatedJob);
}
