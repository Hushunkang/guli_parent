package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程章节表 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Api(description = "课程章节管理API")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //根据课程ID来查询课程下面的课程章节信息和课程小节信息
    @ApiOperation(value = "获取课程章节和课程小节信息")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list",list);
    }

    //根据课程章节ID来查询课程章节信息
    @ApiOperation(value = "查询课程章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@ApiParam(name = "chapterId", value = "课程章节ID", required = true) @PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }

    //添加课程章节
    @ApiOperation(value = "添加课程章节")
    @PostMapping("addChapter")
    public R addChapter(@ApiParam(name = "eduChapter", value = "课程章节信息") @RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.save(eduChapter);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改课程章节
    @ApiOperation(value = "修改课程章节")
    @PostMapping("updateChapter")
    public R updateChapter(@ApiParam(name = "eduChapter", value = "课程章节信息") @RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.updateById(eduChapter);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除课程章节
    @ApiOperation(value = "删除课程章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@ApiParam(name = "chapterId", value = "课程章节ID", required = true) @PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
