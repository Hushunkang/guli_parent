package com.atguigu.eduservice;

import com.atguigu.eduservice.entity.EduTeacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月05日 07时31分07秒
 */
@SpringBootApplication
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduTeacher.class,args);
    }

}
