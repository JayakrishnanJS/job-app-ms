package com.jkdev.jobms.job.mapper;

import com.jkdev.jobms.job.Job;
import com.jkdev.jobms.job.dto.JobWithCompanyDTO;
import com.jkdev.jobms.job.external.Company;

// we need to map the company obj and job obj with the dto obj instead of 2 diff nested obj for job and company in a response
public class JobMapper {
    public static JobWithCompanyDTO mapJobWithCompanyDTO(Job job, Company company) {

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
