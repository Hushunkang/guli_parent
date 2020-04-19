package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日
 */
@SpringBootApplication//默认只在当前模块下扫描，注册bean
@ComponentScan(basePackages = {"com.atguigu"})//不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean
@EnableDiscoveryClient//nacos服务的注册
@EnableFeignClients//feign服务的调用，服务的消费者即微服务的调用方添加此注解
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }

}
