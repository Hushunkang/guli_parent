package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //分页查询讲师数据
    @ApiOperation(value = "分页查询讲师列表")
    //current表示当前页；size表示每页记录数
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                         @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size){
//        IPage<Map<String, Object>> result = eduTeacherService.pageTeacher(current,size);
        Map<String, Object> result = eduTeacherService.pageTeacher(current, size);

        return R.ok().data("result",result);
    }



}
