package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.SubjectLevelOneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file 文件上传时提交的数据以多段的形式进行拼接，最终数据以二进制流的形式发送给服务器
     */
    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    /**
     * 课程分类列表
     * @return
     */
    List<SubjectLevelOneVo> getAllSubject();

}
