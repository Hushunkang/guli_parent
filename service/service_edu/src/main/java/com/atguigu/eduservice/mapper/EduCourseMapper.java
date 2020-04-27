package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.FrontCourseDetailVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程表 Mapper 接口
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程ID查询到要被发布的课程确认信息
     * @param courseId 课程ID
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 根据课程ID查询出前端所需的课程详情
     * @param courseId
     * @return
     */
    FrontCourseDetailVo getBaseCourseInfo(String courseId);

}
