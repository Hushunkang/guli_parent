package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程小节表 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-14
 */
@Api(description = "课程小节管理API")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //添加课程小节
    @ApiOperation(value = "添加课程小节")
    @PostMapping("addVideo")
    public R addVideo(@ApiParam(name = "eduVideo", value = "课程小节信息") @RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除课程小节 todo 后面这个方法需要完善：删除课程小节的同时把它下面的视频删除
    @ApiOperation(value = "删除课程小节")
    @DeleteMapping("{videoId}")
    public R deleteVideo(@ApiParam(name = "videoId", value = "课程小节ID", required = true) @PathVariable String videoId) {
        eduVideoService.removeById(videoId);
        return R.ok();
    }

    //根据课程小节ID来查询课程小节信息
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@ApiParam(name = "videoId", value = "课程小节ID", required = true) @PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改课程小节
    @ApiOperation(value = "修改课程小节")
    @PostMapping("updateVideo")
    public R updateVideo(@ApiParam(name = "eduVideo", value = "课程小节信息") @RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}
