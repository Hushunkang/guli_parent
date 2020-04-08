package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.EduSubjectService;
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
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-08
 */
@Api(description = "课程分类管理API")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的excel文件，然后读取excel文件内容
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(@ApiParam(name = "file", value = "上传的文件") MultipartFile file){
        //上传过来的excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

}

