package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 *
 * 和excel表格所对应的实体类
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日 02时36分04秒
 */
@Data
public class SubjectData {

    @ExcelProperty(value = "一级分类",index = 0)//表示对应Excel表格里面的第一列数据
    private String subjectLevelOneName;//课程分类管理之一级分类名称

    @ExcelProperty(value = "二级分类",index = 1)//表示对应Excel表格里面的第二列数据
    private String subjectLevelTwoName;//课程分类管理之二级分类名称

}
