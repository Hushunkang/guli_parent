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
 * 课程科目表 前端控制器
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

    //课程分类列表（要求返回有层级关系那种树结构的数据模型）
    @ApiOperation(value = "课程分类列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //List集合中的泛型要定义成一级课程分类，因为一级课程分类里关联了二级课程分类，最后返回的数据模型就是依照这样的关系封装的
        List<SubjectLevelOneVo> list = eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }

    //todo 实现删除课程分类节点后端接口，前端同步实现，测试效果
    //需求：一次只能删除一个课程分类节点，如果这个节点下面有子节点，则提示前端不能删除，否则删除成功
    //思路：前端只需要传递节点id这个用户请求参数到后端，后端根据这个id作为parent_id去找记录
    //1、找到了记录，说明这个节点下面有子节点，给前端抛出相应的业务逻辑异常
    //2、没有找到记录，说明这个节点下面不存在子节点，物理删除记录

}
