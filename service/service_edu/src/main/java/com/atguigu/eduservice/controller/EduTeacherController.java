package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-05
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //DI service
    private EduTeacherService eduTeacherService;

    //查询所有讲师数据
    @GetMapping("findAll")//restful风格的url
    public List<EduTeacher> findAllTeacher(){
        //调用service方法实现查询所有讲师数据
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return eduTeachers;
    }

}
