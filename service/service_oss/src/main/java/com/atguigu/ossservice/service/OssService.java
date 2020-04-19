package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日
 */
public interface OssService {

    /**
     * 上传文件到阿里云OSS
     * @param file 上传的文件
     * @return 返回了上传的文件最终的存储的网络地址
     */
    String uploadFileToOss(MultipartFile file);

}
