package com.atguigu.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月18日
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//默认只在当前模块下扫描，注册bean
//(exclude = DataSourceAutoConfiguration.class)表示让spring boot应用启动的时候不去加载数据库配置
@ComponentScan(basePackages = {"com.atguigu"})//不仅仅在当前模块下扫描，还在其依赖的模块下扫描，注册bean
@EnableDiscoveryClient//nacos服务的注册
public class VodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }

}
