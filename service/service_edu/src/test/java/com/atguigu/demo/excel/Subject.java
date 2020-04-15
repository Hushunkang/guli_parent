package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 *
 * easyExcel读操作简单的示例代码
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日
 */
@Data
public class Subject {

    @ExcelProperty(value = "一级分类",index = 0)
    private String subjectLevelOneName;//一级课程分类

    @ExcelProperty(value = "二级分类",index = 1)
    private String subjectLevelTwoName;//课程分类管理之二级分类名称

}
