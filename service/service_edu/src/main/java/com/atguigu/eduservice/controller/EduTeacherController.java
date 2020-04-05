package com.atguigu.eduservice.controller;


import com.atguigu.commonutil.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
                                 @PathVariable Integer id){
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
    public R pageListTeacher(@PathVariable Long current,
                             @PathVariable Long size){
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        eduTeacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> rows = pageTeacher.getRecords();

//        Map<String,Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",rows);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("rows",rows);
    }

}
