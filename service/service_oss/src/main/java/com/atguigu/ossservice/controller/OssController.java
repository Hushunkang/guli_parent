package com.atguigu.ossservice.controller;

import com.atguigu.ossservice.service.OssService;
import com.atguigu.util.R;
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
 * @create 2020年04月07日
 */
@Api(description = "OSS服务API")
@RestController
@RequestMapping("/ossservice/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像
    @ApiOperation(value = "上传文件")
    @PostMapping("uploadFileToOss")//文件上传时提交的数据以多段的形式进行拼接，最终数据以二进制流的形式发送给服务器
    public R uploadFileToOss(@ApiParam(name = "file", value = "上传的文件") MultipartFile file){//获取上传的文件，spring mvc文件上传就这种操作(*￣︶￣)
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url",url);
    }

}
