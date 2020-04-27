package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.enums.VideoStatusEnum;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
        //如果前端传递过来云端视频ID不为空，则表示上传视频到云端是成功的
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            eduVideo.setStatus(VideoStatusEnum.NORMAL.getStatus());
        }
        boolean flag = eduVideoService.save(eduVideo);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除课程小节（删除课程小节的同时要删除它所对应的云端视频）
    @ApiOperation(value = "删除课程小节")
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@ApiParam(name = "videoId", value = "课程小节ID", required = true) @PathVariable String videoId) {
        eduVideoService.deleteVideo(videoId);
        return R.ok();
    }

    //根据课程小节ID来查询课程小节信息
    @ApiOperation(value = "查询课程小节信息")
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@ApiParam(name = "videoId", value = "课程小节ID", required = true) @PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改课程小节
    @ApiOperation(value = "修改课程小节")
    @PostMapping("updateVideo")
    public R updateVideo(@ApiParam(name = "eduVideo", value = "课程小节信息") @RequestBody EduVideo eduVideo) {
        //如果前端传递过来云端视频ID不为空，则表示上传视频到云端是成功的
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            eduVideo.setStatus(VideoStatusEnum.NORMAL.getStatus());
        }
        boolean flag = eduVideoService.updateById(eduVideo);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
