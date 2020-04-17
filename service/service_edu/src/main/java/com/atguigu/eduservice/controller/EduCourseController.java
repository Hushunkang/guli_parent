package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.query.CourseQuery;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseVo;
import com.atguigu.eduservice.enums.CourseStatusEnum;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //多条件组合查询带分页效果的课程数据
    @ApiOperation(value = "多条件组合查询带分页效果的课程列表")
    @PostMapping("pageCourseCondition/{current}/{size}")
    public R pageCourseCondition(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                                 @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size,
                                 @ApiParam(name = "courseQuery", value = "多个查询条件") @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current,size);

        //多条件组合查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，如果不为空则拼接条件，类似mybatis dynamic sql
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        //按照添加时间降序来排序记录
        wrapper.orderByDesc("gmt_create");

        eduCourseService.page(pageCourse,wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    //删除课程数据（删除课程的时候需要删除课程关联的所有数据），最好按照课程小节、课程章节、课程描述、课程的顺序来删除
    @ApiOperation(value = "删除课程数据")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        boolean flag = eduCourseService.deleteCourse(courseId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
