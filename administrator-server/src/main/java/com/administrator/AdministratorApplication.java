package com.administrator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.administrator.dao")
public class AdministratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministratorApplication.class, args);
    }

}
