package com.ns.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ns"})
@EnableJpaRepositories(basePackages = {"com.ns"})
@EntityScan(basePackages = {"com.ns"})
@MapperScan(basePackages = {"com.ns.common.dao", "com.ns.ejk.form.dao"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ns.ejk.form.rcp.feign"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
