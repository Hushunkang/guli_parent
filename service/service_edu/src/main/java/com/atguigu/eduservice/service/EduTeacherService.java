package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-05
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 查询前4名讲师
     * @return
     */
    List<EduTeacher> getTeachers();

    /**
     * 分页查询讲师数据
     * @param current
     * @param size
     * @return
     */
    Map<String, Object> pageTeacher(Long current,Long size);

}
