package com.zerobase.cms.zerobasecms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing
//@EnableJpaRepositories
public class ZerobaseCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZerobaseCmsApplication.class, args);
    }

}
