package com.atguigu.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.demo.excel.Subject;

import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日
 */
public class ExcelListener extends AnalysisEventListener<Subject> {

    //一行一行读取excel文件中的数据
    @Override
    public void invoke(Subject data, AnalysisContext context) {
        System.out.println("***" + data);
    }

    //读取excel表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    //读完excel后干的事儿
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("doAfterAllAnalysed...");
    }

}
