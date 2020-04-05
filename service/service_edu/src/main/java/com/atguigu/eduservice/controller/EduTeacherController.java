package com.atguigu.eduservice.controller;


import com.atguigu.commonutil.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
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
 * 讲师 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-05
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
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
        return R.ok().data("items",eduTeachers);
    }

    //删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                 @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询所有讲师数据
    @ApiOperation(value = "分页查询讲师列表")
    //current表示当前页；size表示每页记录数
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageTeacher(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                         @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size){
        Page<EduTeacher> pageTeacher = new Page<>(current,size);

        int i = 10 / 0;//手动模拟一个运行时异常

        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> rows = pageTeacher.getRecords();

//        Map<String,Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",rows);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("rows",rows);
    }

    //多条件组合查询带分页效果的讲师数据
    @ApiOperation(value = "多条件组合查询带分页效果的讲师列表")
    @PostMapping("pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                                  @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size,
                                  @ApiParam(name = "teacherQuery", value = "多个查询条件") @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空则拼接条件，这里类似mybatis dynamic sql
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
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> rows = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",rows);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "eduTeacher", value = "添加讲师json数据") @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id查询讲师
    @ApiOperation(value = "根据讲师表的主键查询讲师")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "修改讲师json数据") @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
