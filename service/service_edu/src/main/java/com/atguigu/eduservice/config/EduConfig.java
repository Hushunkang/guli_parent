package com.atguigu.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日 07时33分39秒
 */
@Configuration
@MapperScan("com.atguigu.eduservice.mapper")//说明：很重要！！！这一步其实是将mybatis动态生成的dao层Mapper接口的实现类注册到spring ioc容器里面，要用的话就di
public class EduConfig {
}
