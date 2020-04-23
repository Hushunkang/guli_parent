package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程表 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseVo
     */
    String addCourseInfo(CourseVo courseVo);

    /**
     * 根据课程ID查询课程基本信息
     * @param courseId 课程ID
     * @return
     */
    CourseVo getCourseInfo(String courseId);

    /**
     * 修改课程基本信息
     * @param courseVo
     * @return
     */
    boolean updateCourseInfo(CourseVo courseVo);

    /**
     * 根据课程ID查询到要被发布的课程确认信息
     * @param courseId 课程ID
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 删除课程数据
     * @param courseId 课程ID
     */
    boolean deleteCourse(String courseId);

    /**
     * 查询前8门热门课程
     * @return
     */
    List<EduCourse> getCourses();

}
