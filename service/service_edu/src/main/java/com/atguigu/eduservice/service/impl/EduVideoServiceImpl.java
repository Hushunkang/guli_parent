package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程小节表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
        //todo 完善，根据课程ID删除一个课程下面所有课程小节数据信息后需要删除这些课程小节所对应的全部云端视频

    }

    @Override
    public void deleteVideo(String videoId) {//videoId指的是课程小节ID不是云端视频ID，不要误解了
        //根据课程小节ID找到其所对应的云端视频ID
        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();

        //删除课程小节所对应的云端视频（注意：传统的方式可以给删除云端视频的业务代码写这，但是现在使用微服务调用的方式）
        //edu这个微服务中的方法调用vod这个微服务中的方法
        //用户请求的还是这个edu微服务（独立的进程），但是它远程调用了vod微服务（也是独立的进程）
//        vodClient.removeVideo("云端视频ID");
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.removeVideo(videoSourceId);//底层原理大概是找对应服务，远程过程调用
            if (result.getCode() == 20000) {//表示RPC成功，成功就不会触发hystrix熔断机制
                log.info("RPC成功...");
            } else {
                log.info("RPC失败...触发熔断机制...");
                log.info("结果为：" + result);
            }
        }//说明，vodClient.removeVideo(videoSourceId);执行这个方法的时候如果出现异常了，当前的微服务默认感知不到，原因是因为“独立的进程”，一个进程仅仅只是远程调用了另一个进程

        //根据课程小节ID删除课程小节记录
        baseMapper.deleteById(videoId);
    }

}
