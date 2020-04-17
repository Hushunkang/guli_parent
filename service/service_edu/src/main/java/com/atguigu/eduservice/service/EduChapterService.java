package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程章节表 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程ID来查询课程下面的课程章节信息和课程小节信息
     * @param courseId 课程ID
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除课程章节
     * @param chapterId 课程章节ID
     * @return
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除章节
     * @param courseId 课程ID
     */
    void removeChapterByCourseId(String courseId);

}
