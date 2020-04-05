package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师数据
    @GetMapping("findAll")//restful风格的url
    public List<EduTeacher> findAllTeacher(){
        //调用service方法实现查询所有讲师数据
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return eduTeachers;
    }

    //删除讲师
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        return false;
    }

}
