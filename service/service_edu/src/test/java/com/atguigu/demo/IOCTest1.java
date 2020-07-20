package com.atguigu.demo;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年06月29日
 */
public class IOCTest1 {

    @Test
    public void testIOC1(){
        AnnotationConfigApplicationContext applicatoin = new AnnotationConfigApplicationContext(BeanConfig.class);//正在创建IOC容器（启动IOC容器过程中）
        System.out.println("IOC容器创建完成... ...");

        String[] names = applicatoin.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        Object computer1 = applicatoin.getBean("testComputer");
        System.out.println(computer1);

        Object computer2 = applicatoin.getBean("testComputer");
        System.out.println(computer2 == computer1);

        applicatoin.close();//关闭IOC容器
        System.out.println("IOC容器关闭了... ...");
    }

}
