package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public R addSubject(MultipartFile file){
        //上传过来的excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

}

