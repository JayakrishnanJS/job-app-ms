package com.jkdev.jobms.job.mapper;

import com.jkdev.jobms.job.Job;
import com.jkdev.jobms.job.dto.JobDTO;
import com.jkdev.jobms.job.external.Company;
import com.jkdev.jobms.job.external.Review;

import java.util.List;

// we need to map the company obj, job obj and review list obj with the dto obj
public class JobMapper {
    public static JobDTO mapJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
