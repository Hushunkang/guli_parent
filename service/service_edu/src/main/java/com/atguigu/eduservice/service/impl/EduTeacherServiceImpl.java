package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    @Cacheable(value = "teacher", key = "'getTeachers'")
    public List<EduTeacher> getTeachers() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.last("limit 4");
        List<EduTeacher> teachers = baseMapper.selectList(wrapper);
        return teachers;
    }

    @Override
    public Map<String, Object> frontPageTeacher(Long current,Long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageTeacher, wrapper);

        current = pageTeacher.getCurrent();
        size = pageTeacher.getSize();
        long pages = pageTeacher.getPages();
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        boolean hasNext = pageTeacher.hasNext();//当前页是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//当前页是否有上一页

        //将分页模型里面的属性全部取出来，放到map集合里面，用于返回给前端
        Map<String, Object> result = new HashMap<>();
        result.put("current", current);
        result.put("size", size);
        result.put("pages", pages);
        result.put("total", total);
        result.put("records", records);
        result.put("hasNext", hasNext);
        result.put("hasPrevious", hasPrevious);

        return result;
    }

}
