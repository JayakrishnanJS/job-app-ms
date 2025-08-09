package com.jkdev.jobms.job;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @LoadBalanced
    @Bean // to tell SB to manage load balanced objs of the class
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

/**
 * Configures a load-balanced RestTemplate bean to be managed by Spring.
 * - Load balancing is the process of distributing incoming network requests or traffic across multiple
 *   servers or instances to ensure that no single server is overwhelmed.
 * Instead of providing a direct URL of the service, you provide the logical service name in your requests.
 * For example: 2nd one replace 1st one
 *         Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
 *         Company company = restTemplate.getForObject("http://Company-Service/companies/" + job.getCompanyId(), Company.class);
 */