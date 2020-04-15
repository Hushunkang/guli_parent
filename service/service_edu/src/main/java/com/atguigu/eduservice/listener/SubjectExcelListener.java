package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //特别注意：SubjectExcelListener的对象实例是手动new出来，它不是交给spring ioc容器管理
    //因此，SubjectExcelListener使用@Autowire注入Service是不行的，那么可以用构造器的方式来曲线救国
    //https://blog.csdn.net/zbajie001/article/details/52063643

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //一行一行读取excel文件中的数据，因此这个invoke方法会被调用很多次
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {//data表示excel文件中的一行数据
        if(data == null){
            throw new GuliException(ResultCode.ERROR,"文件中没有数据(⊙︿⊙)");//文案工作
        }

        //添加一级课程
        //判断这个一级课程是否已经添加，已经添加就不能重复添加
        EduSubject existSubjectLevelOne = existSubjectLevelOne(eduSubjectService, data.getSubjectLevelOneName());
        if(existSubjectLevelOne == null) {//一级课程没有被添加
            existSubjectLevelOne = new EduSubject();
            existSubjectLevelOne.setParentId("0");
            existSubjectLevelOne.setTitle(data.getSubjectLevelOneName());//一级课程名称
            eduSubjectService.save(existSubjectLevelOne);
        }

        //获取一级课程ID
        String parentId = existSubjectLevelOne.getId();

        //添加二级课程
        //判断这个二级课程是否已经添加，已经添加就不能重复添加
        EduSubject existSubjectLevelTwo = existSubjectLevelTwo(eduSubjectService, data.getSubjectLevelTwoName(), parentId);
        if(existSubjectLevelTwo == null) {
            existSubjectLevelTwo = new EduSubject();
            existSubjectLevelTwo.setParentId(parentId);
            existSubjectLevelTwo.setTitle(data.getSubjectLevelTwoName());//二级课程名称
            eduSubjectService.save(existSubjectLevelTwo);
        }
    }

    //课程科目表是一种树体系结构的表
    //     并且它是多根节点（多个一级分类）的那种类型，类似于Windows操作系统的体系结构
    //     如果它是单根节点（只有一个一级分类）的那种类型，类似于Linux操作系统的体系结构
    //     树的深度为2（我没理解错应该是这样，目前数据结构基础不是很厉害，后期学习强化一下）
    //根据一级课程名称来找，看看是不是已经添加了
    private EduSubject existSubjectLevelOne(EduSubjectService subjectService,String subjectName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id","0");//0表示一级课程
        EduSubject subjectOne = subjectService.getOne(wrapper);
        return subjectOne;
    }

    //根据二级课程名称和它的父ID来找，看看是不是已经添加了
    private EduSubject existSubjectLevelTwo(EduSubjectService subjectService,String subjectName,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",parentId);
        EduSubject subjectTwo = subjectService.getOne(wrapper);
        return subjectTwo;
    }

    //读取excel表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    //读完excel后干的事儿
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("doAfterAllAnalysed...");
    }

}
