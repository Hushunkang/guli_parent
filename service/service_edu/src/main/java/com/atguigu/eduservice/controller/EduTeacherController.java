package com.atguigu.eduservice.controller;


import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.query.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.util.R;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师表 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-05
 */
@Api(description = "讲师管理API")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //di service
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询所有讲师数据
    @ApiOperation(value = "讲师列表")
    @GetMapping("findAll")//restful风格的url
    public R findAllTeacher(){
        //调用service方法实现查询所有讲师数据
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return R.ok().data("list",eduTeachers);
    }

    //根据讲师ID查询讲师
    @ApiOperation(value = "根据讲师ID查询讲师")
    @GetMapping("getTeacherById/{teacherId}")
    public R getTeacherById(@ApiParam(name = "teacherId", value = "讲师ID", required = true) @PathVariable("teacherId") String teacherId){
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    //分页查询所有讲师数据
    @ApiOperation(value = "分页查询讲师列表")
    //current表示当前页；size表示每页记录数
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable("current") Long current,
                         @ApiParam(name = "size", value = "每页记录数") @PathVariable("size") Long size){
        Page<EduTeacher> pageTeacher = new Page<>(current,size);

//        int i = 10 / 0;//手动模拟一个运行时异常

        //业务上面要求每页的大小不能超过100
        if(size > 100){
            throw new GuliException(ResultCode.ERROR,"分页操作每页的大小不能超过100(⊙︿⊙)");
        }

        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

//        Map<String,Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("records",records);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("records",records);
    }

    //多条件组合查询带分页效果的讲师数据
    @ApiOperation(value = "多条件组合查询带分页效果的讲师列表")
    @PostMapping("pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页") @PathVariable("current") Long current,
                                  @ApiParam(name = "size", value = "每页记录数") @PathVariable("size") Long size,
                                  @ApiParam(name = "teacherQuery", value = "多个查询条件") @RequestBody(required = false) TeacherQuery teacherQuery){
        //todo 最好code refactor一下，业务逻辑写在Service层，后端模块化分层的思想，一来结构清晰，各层有各层的用处，二来实现Service层代码共用
        Page<EduTeacher> pageTeacher = new Page<>(current,size);

        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，如果不为空则拼接条件，类似mybatis dynamic sql
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        //按照添加时间降序来排序记录
        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "eduTeacher", value = "讲师信息") @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("removeTeacher/{teacherId}")
    public R removeTeacher(@ApiParam(name = "teacherId", value = "讲师ID", required = true)
                           @PathVariable("teacherId") String teacherId){
        boolean flag = eduTeacherService.removeById(teacherId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "讲师信息") @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
