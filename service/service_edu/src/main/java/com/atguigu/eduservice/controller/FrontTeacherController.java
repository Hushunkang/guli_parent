package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月27日
 */
@Api(description = "前台系统讲师API")
@RestController
@RequestMapping("/eduservice/frontteacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师数据
    @ApiOperation(value = "分页查询讲师列表")
    //current表示当前页；size表示每页记录数
    @GetMapping("frontPageTeacher/{current}/{size}")
    public R frontPageTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                         @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size){
//        IPage<Map<String, Object>> result = eduTeacherService.pageTeacher(current,size);
        Map<String, Object> result = eduTeacherService.frontPageTeacher(current, size);

        return R.ok().data("result",result);
    }

    //根据讲师ID查询讲师
    @ApiOperation(value = "根据讲师ID查询讲师")
    @GetMapping("frontGetTeacherById/{teacherId}")
    public R frontGetTeacherById(@ApiParam(name = "teacherId", value = "讲师ID", required = true) @PathVariable String teacherId){
        //根据讲师ID查询讲师
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        //根据讲师ID查询讲师所讲课程
        List<EduCourse> courses = eduCourseService.getCoursesByTeacherId(teacherId);

        return R.ok().data("eduTeacher",eduTeacher).data("courses",courses);
    }

}
