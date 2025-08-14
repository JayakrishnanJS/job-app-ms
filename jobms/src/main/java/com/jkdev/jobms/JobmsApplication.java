package com.jkdev.jobms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class JobmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobmsApplication.class, args);
	}

}
// The `@RateLimiter` annotation relies on AOP to intercept method calls and apply rate-limiting logic.
// Without `@EnableAspectJAutoProxy`, the AOP proxy might not be enabled, which means that the `@RateLimiter`
// (or similar annotations) are not applied.
