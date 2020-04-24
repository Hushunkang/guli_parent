package com.atguigu.ucenterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
@SpringBootApplication//默认只在当前模块下扫描，注册bean
@ComponentScan(basePackages = {"com.atguigu"})//不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class,args);
    }

}
