package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程小节表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
        //todo 完善，根据课程ID删除一个课程下面所有课程小节数据信息后需要删除这些课程小节所对应的全部云端视频

    }

    @Override
    public void deleteVideo(String videoId) {
        //根据课程小节ID删除课程小节记录
        baseMapper.deleteById(videoId);
        //删除课程小节所对应的云端视频（注意：传统的方式可以给删除云端视频的业务代码写这，但是现在使用微服务调用的方式）
        //edu这个微服务中的方法调用vod这个微服务中的方法

    }

}
