package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月22日
 */
@Api(description = "前台系统首页API")
@RestController
@RequestMapping("/eduservice/frontindex")
@CrossOrigin
public class FrontIndexController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8门热门课程以及查询前4名讲师（按照添加时间）
    @ApiOperation(value = "首页热门课程以及讲师展示")
    @GetMapping("index")
    public R index() {
        //查询前8门热门课程
        List<EduCourse> courseList = eduCourseService.getCourses();

        //查询前4名讲师
        List<EduTeacher> teacherList = eduTeacherService.getTeachers();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
