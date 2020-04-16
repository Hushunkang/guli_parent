package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.entity.vo.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程章节表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //最终的数据模型，即最终需要给前端返回的信息
        List<ChapterVo> finalList = new ArrayList<>();

        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            finalList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if(eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //业务上面的要求，根据课程章节ID去课程小节表里面找对应的数据，如果找的到，则不能删除课程章节
        int count = eduVideoService.count(wrapper);

        if(count >0) {//课程章节下面有小节，不能进行删除
            throw new GuliException(ResultCode.ERROR,"删除失败，章节下存在课时");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

}
