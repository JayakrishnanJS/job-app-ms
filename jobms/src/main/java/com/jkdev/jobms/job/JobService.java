package com.jkdev.jobms.job;

import com.jkdev.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void addJob(Job job);

    JobDTO getJobById(Long id);
    boolean deleteJobById(Long id);

    boolean updateJobById(Long id, Job updatedJob);
}
