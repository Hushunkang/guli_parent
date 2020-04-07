package com.atguigu.ossservice.controller;

import com.atguigu.ossservice.service.OssService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @create 2020年04月07日 19时56分29秒
 */
@Api(description = "OSS服务API")
@RestController
@RequestMapping("/ossservice/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像
    @ApiOperation(value = "上传头像")
    @PostMapping("uploadFileToOss")
    public R uploadFileToOss(MultipartFile file){//获取上传的文件，spring mvc文件上传就这种操作(*￣︶￣)
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url",url);
    }

}
