package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseVo;
import com.atguigu.eduservice.enums.CourseStatusEnum;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private VodClient vodClient;

    @Override
    public String addCourseInfo(CourseVo courseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);
        //课程表中添加数据
        int insert = baseMapper.insert(eduCourse);//影响的行数
        if (insert <= 0) {
            //添加失败
            throw new GuliException(ResultCode.ERROR,"添加课程基本信息失败(⊙︿⊙)");
        }

        //获取添加课程后课程ID
        String courseId = eduCourse.getId();

        //课程简介表添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //手动设置课程描述ID，让课程表和课程简介表构成1对1的关系
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseVo.getDescription());
        boolean flag = eduCourseDescriptionService.save(eduCourseDescription);
        if (!flag) {
            throw new GuliException(ResultCode.ERROR,"添加课程描述基本信息失败(⊙︿⊙)");
        }

        return courseId;
    }

    @Override
    public CourseVo getCourseInfo(String courseId) {
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(eduCourse,courseVo);

        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseVo.setDescription(courseDescription.getDescription());

        return courseVo;
    }

    @Override
    public boolean updateCourseInfo(CourseVo courseVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);

        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new GuliException(ResultCode.ERROR,"修改课程基本信息失败(⊙︿⊙)");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseVo.getId());
        description.setDescription(courseVo.getDescription());
        return eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(courseId);
        return coursePublishVo;
    }

    @Override
    public boolean deleteCourse(String courseId) {
        //查询出这个课程下面所有小节的所有云端视频ID，然后拼串
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.select("video_source_id").eq("course_id",courseId);//mp只查询这一列数据video_source_id
        List<EduVideo> eduVideos = eduVideoService.list(wrapper);
        List<String> videoSourceIds = new ArrayList<>();
        //todo 利用Java8新特性Stream API来操作集合，简化操作
        for (EduVideo eduVideo : eduVideos) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())) {
                videoSourceIds.add(eduVideo.getVideoSourceId());
            }
        }
        String vodIds = String.join(",",videoSourceIds);

        log.info("将要被删除的云端视频IDS：" + vodIds);

        if (!StringUtils.isEmpty(vodIds)) {
            //远程调用
            vodClient.removeVideo(vodIds);
        }

        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);//课程ID和课程描述ID一一对应，因此课程ID等于课程描述ID

        //根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) {//注意虽然删除不存在的记录，逻辑上属于成功，但是这里的result指的是数据库操作返回影响条数，这里的result为0表示没有删除任何记录
            throw new GuliException(20001,"删除课程失败(⊙︿⊙)");
        }else{
            return result > 0;
        }
    }

    @Override
    @Cacheable(value = "course", key = "'getCourses'")
    public List<EduCourse> getCourses() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("status", CourseStatusEnum.NORMAL.getStatus());//已经发布的课程才可以在前台系统查询的到
        wrapper.last("limit 8");
        List<EduCourse> courses = baseMapper.selectList(wrapper);
        return courses;
    }

}
