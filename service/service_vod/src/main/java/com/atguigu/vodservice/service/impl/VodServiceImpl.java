package com.atguigu.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.atguigu.vodservice.service.VodService;
import com.atguigu.vodservice.util.ConstantVodUtils;
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

}
