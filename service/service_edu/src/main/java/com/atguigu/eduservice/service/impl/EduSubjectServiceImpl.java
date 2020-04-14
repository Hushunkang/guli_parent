package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.SubjectLevelOne;
import com.atguigu.eduservice.entity.subject.SubjectLevelTwo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            //new SubjectExcelListener，表明这个Bean（组件）没有交给spring ioc容器来管理，说白了就是没有给这个组件注册到spring ioc容器里面
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectLevelOne> getAllSubject() {
        //1、查询出所有的一级分类课程，parent_id=0
        QueryWrapper<EduSubject> wapperLevelOne = new QueryWrapper<>();
        wapperLevelOne.eq("parent_id","0");
        List<EduSubject> subjectLevelOnes = baseMapper.selectList(wapperLevelOne);

        //2、查询出所有的二级分类课程，parent_id!=0
        QueryWrapper<EduSubject> wapperLevelTwo = new QueryWrapper<>();
        wapperLevelTwo.ne("parent_id","0");
        List<EduSubject> subjectLevelTwos = baseMapper.selectList(wapperLevelTwo);

        //将数据封装成前端所期望的数据模型
        List<SubjectLevelOne> finalOnes = new ArrayList<>();
        List<SubjectLevelTwo> finalTwos = new ArrayList<>();
        for (EduSubject subjectLevelOne : subjectLevelOnes) {
            SubjectLevelOne one = new SubjectLevelOne();
//            one.setId(subjectLevelOne.getId());
//            one.setTitle(subjectLevelOne.getTitle());

            //使用spring提供的api简化操作
            BeanUtils.copyProperties(subjectLevelOne,one);
            //在每一个一级分类下面，封装二级分类的数据
            for (EduSubject subjectLevelTwo : subjectLevelTwos) {
                if (subjectLevelOne.getId().equals(subjectLevelTwo.getParentId())) {
                    SubjectLevelTwo two = new SubjectLevelTwo();
                    BeanUtils.copyProperties(subjectLevelTwo,two);
                    finalTwos.add(two);
                }
            }
            one.setChildren(finalTwos);
            finalOnes.add(one);
        }
        return finalOnes;
    }

}
