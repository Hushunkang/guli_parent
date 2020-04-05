package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //di service
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师数据
    @ApiOperation(value = "讲师列表")
    @GetMapping("findAll")//restful风格的url
    public List<EduTeacher> findAllTeacher(){
        //调用service方法实现查询所有讲师数据
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return eduTeachers;
    }

    //删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public boolean removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                 @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        return flag;
    }

}
