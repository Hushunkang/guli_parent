package com.atguigu.ossservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//默认只在当前模块下扫描，注册bean
//(exclude = DataSourceAutoConfiguration.class)表示让spring boot应用启动的时候不去加载数据库配置
@ComponentScan(basePackages = {"com.atguigu"})//不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean
@EnableFeignClients
public class OssAppliaction {

    public static void main(String[] args) {
        SpringApplication.run(OssAppliaction.class,args);
    }

}
