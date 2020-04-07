package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日 19时56分41秒
 */
public interface OssService {

    /**
     * 上传头像
     * @param file 上传的头像文件
     * @return 返回了上传的头像文件最终的存储的网络地址
     */
    String uploadAvatar(MultipartFile file);

}
