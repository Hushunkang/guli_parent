package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.ossservice.service.OssService;
import com.atguigu.ossservice.util.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月07日
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file) {

        // 获取Bucket存储空间名称
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
//        InputStream inputStream = new FileInputStream("<yourlocalFile>");
            InputStream inputStream = file.getInputStream();

            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();

            //bug：上传同名的文件，后者会覆盖前者的问题！！！
            //1、采用拼接UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            //2、将上传的文件按照日期来分类
            StringBuilder datePath = new StringBuilder(new DateTime().toString("yyyy/MM/dd"));
            String fileName = datePath.append("/").append(uuid).append(originalFilename).toString();


            //putObject方法第一个参数指的是存储空间名称
            //第二个参数指的是文件上传到oss的路径和名称
            //第三个参数指的是流式上传的上传文件流（流的理解：是数据存在的一种形式）
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传到阿里云oss的文件所在路径手动拼接出来

            //https://hsk-virtuoso-edu-guli.oss-cn-hangzhou.aliyuncs.com/0.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
