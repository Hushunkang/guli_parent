package com.atguigu.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月18日
 */
public interface VodService {

    /**
     * 上传视频
     * @param file 上传的视频文件
     * @return 返回了上传的视频文件的视频ID（阿里云的视频点播服务为每一个上传的视频生成的视频ID）
     */
    String uploadVideo(MultipartFile file);

}
