package com.atguigu.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.util.R;
import com.atguigu.util.ResultCode;
import com.atguigu.vodservice.service.VodService;
import com.atguigu.vodservice.util.ConstantVodUtils;
import com.atguigu.vodservice.util.InitVodClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月18日
 */
@Api(description = "VOD服务API")
@RestController
@RequestMapping("/vodservice/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云视频点播服务，即上传视频到云端
    @ApiOperation(value = "上传视频到云端")
    @PostMapping("uploadVideo")
    public R uploadVideo(@ApiParam(name = "file", value = "上传的文件") MultipartFile file) {
        String videoId = vodService.uploadVideo(file);//返回视频ID
        return R.ok().data("videoId",videoId);
    }

    //根据阿里云视频点播服务为每一个视频生成的视频ID来删除云端视频，支持删除至少为零个的视频
    @ApiOperation(value = "删除云端视频")
    @DeleteMapping("removeVideo/{videoSourceIds}")
    public R removeVideo(@ApiParam(name = "videoSourceIds", value = "云端视频IDS", required = true) @PathVariable("videoSourceIds") String videoSourceIds){
        vodService.removeVideo(videoSourceIds);
        return R.ok();
    }

    //根据阿里云视频点播服务为每一个视频生成的视频ID来获取视频播放凭证（补充一下，每一个云端视频的视频播放凭证是一样的，我测试发现是这样的）
    @ApiOperation(value = "根据阿里云视频点播服务为每一个视频生成的视频ID来获取视频播放凭证")
    @GetMapping("getVideoPlayAuth/{videoSourceId}")
    public R getVideoPlayAuth(@ApiParam(name = "videoSourceId", value = "云端视频ID", required = true) @PathVariable("videoSourceId") String videoSourceId){
        try {
            //初始化一个vod的客户端
            DefaultAcsClient client = InitVodClientUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取视频播放凭证的request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向视频播放凭证的request对象里面设置云端视频ID
            request.setVideoId(videoSourceId);
            //创建获取视频播放凭证的response对象
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //从响应数据里面获取到视频播放凭证
            String videoPlayAuth = response.getPlayAuth();
            return R.ok().data("videoPlayAuth",videoPlayAuth);
        }catch(Exception e) {
            throw new GuliException(ResultCode.ERROR,"获取视频播放凭证失败，无法播放此视频(⊙︿⊙)");
        }
    }

}
