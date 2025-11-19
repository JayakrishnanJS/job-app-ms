package com.jkdev.jobms.job.clients;

import com.jkdev.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANY-SERVICE", url = "${company-service.url}")
public interface CompanyClient {
    //Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);
    // for new method which eases above line in impl ->Company as returnType, job.getCompanyId() as parameter id
    // add mapping similar to company controller getCompanyById api
    @GetMapping("/companies/{id}")
    Company getCompany(@PathVariable("id") Long id);
}
// FeignClients will take the responsibility of calling appropriate ms from restTemplate and boilerplate code