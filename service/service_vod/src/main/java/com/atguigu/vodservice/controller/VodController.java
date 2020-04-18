package com.atguigu.vodservice.controller;

import com.atguigu.util.R;
import com.atguigu.vodservice.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    //上传视频到阿里云视频点播服务
    @ApiOperation(value = "上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(@ApiParam(name = "file", value = "上传的文件") MultipartFile file) {
        String videoId = vodService.uploadVideo(file);//返回视频ID
        return R.ok().data("videoId",videoId);
    }

}
