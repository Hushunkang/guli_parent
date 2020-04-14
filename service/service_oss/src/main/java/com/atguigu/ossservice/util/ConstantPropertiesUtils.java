package com.atguigu.ossservice.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日
 */
//当模块启动的时候，会初始化各种spring ioc容器，而ConstantPropertiesUtils组件交给spring ioc容器管理，spring ioc容器来管理Bean的生命周期
//spring里面有个很重要的接口InitializingBean，这个接口有一个方法afterPropertiesSet
//这个方法是当Bean的对象实例的属性初始化后执行，做一些事情，详情可以看官方文档、API等资料
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    public ConstantPropertiesUtils() {
        System.out.println("ConstantPropertiesUtils's constructor...");
    }

    //aliyun oss 存储空间名称
    //这个注解给属性赋值，底层原理还是用了反射，反射提供了非常强大的API，可以说“无所不能”！！！
    //以前学过Java基础阶段应该知道，很“神奇”的操作，反射API可以在当前类以外访问当前类私有的机构！！！
    //反射和封装性并不矛盾的解释，可以看看自己总结的那一段笔记(*￣︶￣)
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //aliyun oss 地域节点
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    //aliyun oss AccessKeyId
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    //aliyun oss AccessKeySecret
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    //定义公开的静态变量
    public static String BUCKET_NAME;
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        BUCKET_NAME = bucketName;
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }

}
