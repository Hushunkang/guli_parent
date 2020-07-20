package com.atguigu.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年06月29日
 */
@Configuration
public class BeanConfig {

    @Bean(name = "testComputer",initMethod = "init",destroyMethod = "destroy")
//    @Lazy
    @Scope(value = "prototype")
    public Computer computer() {
        return new Computer("Latitude","Dell");
    }

}
