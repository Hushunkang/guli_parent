package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //课程表中添加数据
        int insert = baseMapper.insert(eduCourse);//影响的行数
        if (insert <= 0) {
            //添加失败
            throw new GuliException(ResultCode.ERROR,"添加课程信息失败(⊙︿⊙)");
        }

        //获取添加课程后课程ID
        String courseId = eduCourse.getId();

        //课程简介表添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //手动设置课程描述ID，让课程表和课程简介表构成1对1的关系
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

}
