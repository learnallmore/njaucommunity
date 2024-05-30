package com.design.njaucommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NjaucommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(NjaucommunityApplication.class, args);
    }

}
