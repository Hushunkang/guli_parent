package com.atguigu.eduservice.controller;

import com.atguigu.eduservice.entity.vo.FrontCourseVo;
import com.atguigu.eduservice.service.EduCourseService;
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
@Api(description = "前台系统课程API")
@RestController
@RequestMapping("/eduservice/frontcourse")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //分页查询课程数据
    @ApiOperation(value = "分页查询课程数据")
    //current表示当前页；size表示每页记录数
    @PostMapping("frontPageCourse/{current}/{size}")
    public R frontPageCourse(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                                @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size,
                                @RequestBody(required = false) FrontCourseVo frontCourseVo) {
        Map<String, Object> result = eduCourseService.frontPageCourse(current, size, frontCourseVo);

        return R.ok().data("result",result);
    }

}
