package com.atguigu.eduservice.controller;


import com.atguigu.eduservice.entity.vo.SubjectLevelOneVo;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    //课程分类列表（要求返回的数据模型是树结构的）
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //List集合中的泛型要定义成一级分类的，因为一级分类里关联了二级分类
        List<SubjectLevelOneVo> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }

}
