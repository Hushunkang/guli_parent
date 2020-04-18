package com.atguigu.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.util.ResultCode;
import com.atguigu.vodservice.service.VodService;
import com.atguigu.vodservice.util.ConstantVodUtils;
import com.atguigu.vodservice.util.InitVodClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月18日
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {//文件上传的方式：使用流式上传接口
        try {
            String fileName = file.getOriginalFilename();//上传文件的原始名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));//文件上传之后的名称
            InputStream inputStream = file.getInputStream();//上传文件的输入流
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeVideo(String videoSourceId) {
        try {
            //初始化一个vod的客户端
            DefaultAcsClient client = InitVodClientUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //向删除视频的request对象里面设置云端视频ID
            deleteVideoRequest.setVideoIds(videoSourceId);
//            DeleteVideoResponse deleteVideoResponse = client.getAcsResponse(deleteVideoRequest);
            client.getAcsResponse(deleteVideoRequest);
        } catch (ClientException e) {
//            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR,"删除云端视频失败(⊙︿⊙)");
        }
    }

}
