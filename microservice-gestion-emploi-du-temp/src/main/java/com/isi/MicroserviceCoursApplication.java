package com.isi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
@EnableAsync
@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MicroserviceCoursApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCoursApplication.class, args);
    }

}
