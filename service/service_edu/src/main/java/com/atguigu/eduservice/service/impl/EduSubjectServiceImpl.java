package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.vo.SubjectLevelOneVo;
import com.atguigu.eduservice.entity.vo.SubjectLevelTwoVo;
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
            //new SubjectExcelListener，表明这个组件没有交给spring ioc容器来管理，没有给这个组件注册到spring ioc容器里面
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectLevelOneVo> getAllSubject() {
        QueryWrapper<EduSubject> wapperLevelOne = new QueryWrapper<>();
        wapperLevelOne.eq("parent_id","0");
        List<EduSubject> subjectLevelOnes = baseMapper.selectList(wapperLevelOne);

        QueryWrapper<EduSubject> wapperLevelTwo = new QueryWrapper<>();
        wapperLevelTwo.ne("parent_id","0");
        List<EduSubject> subjectLevelTwos = baseMapper.selectList(wapperLevelTwo);

        //最终的数据模型，即最终需要给前端返回的信息
        List<SubjectLevelOneVo> finalOnes = new ArrayList<>();

        for (EduSubject subjectLevelOne : subjectLevelOnes) {
            SubjectLevelOneVo one = new SubjectLevelOneVo();
//            one.setId(subjectLevelOne.getId());
//            one.setTitle(subjectLevelOne.getTitle());

            //使用spring提供的api简化操作
            BeanUtils.copyProperties(subjectLevelOne,one);

            finalOnes.add(one);

            List<SubjectLevelTwoVo> finalTwos = new ArrayList<>();
            for (EduSubject subjectLevelTwo : subjectLevelTwos) {
                if (subjectLevelOne.getId().equals(subjectLevelTwo.getParentId())) {
                    SubjectLevelTwoVo two = new SubjectLevelTwoVo();
                    BeanUtils.copyProperties(subjectLevelTwo,two);
                    finalTwos.add(two);
                }
            }
            one.setChildren(finalTwos);
        }
        return finalOnes;
    }

}
