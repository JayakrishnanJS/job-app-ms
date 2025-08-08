package com.jkdev.jobms.job.dto;

import com.jkdev.jobms.job.Job;
import com.jkdev.jobms.job.external.Company;

// DTO pattern is used to combine objects of 2 ms and return as a combined response
public class JobWithCompanyDTO {
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    private Job job;
    private Company company;
}
