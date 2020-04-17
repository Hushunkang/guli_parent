package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseVo;
import com.atguigu.eduservice.enums.CourseStatusEnum;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Api(description = "课程管理API")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@ApiParam(name = "courseVo", value = "课程基本信息") @RequestBody CourseVo courseVo){
        String courseId = eduCourseService.addCourseInfo(courseVo);
        return R.ok().data("courseId",courseId);
    }

    //根据课程ID查询课程基本信息
    @ApiOperation(value = "查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        CourseVo courseVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseVo",courseVo);
    }

    //修改课程基本信息
    @ApiOperation(value = "修改课程基本信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@ApiParam(name = "courseVo", value = "课程基本信息") @RequestBody CourseVo courseVo) {
        boolean flag = eduCourseService.updateCourseInfo(courseVo);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据课程ID查询到要被发布的课程确认信息
    @ApiOperation(value = "课程确认信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    //发布课程
    @ApiOperation(value = "发布课程")
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(CourseStatusEnum.NORMAL.getStatus());//设置课程状态
        boolean flag = eduCourseService.updateById(eduCourse);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
