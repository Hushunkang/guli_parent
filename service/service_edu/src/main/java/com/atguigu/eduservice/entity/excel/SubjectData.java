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
 * @create 2020年04月08日
 */
@Data
public class SubjectData {

    @ExcelProperty(value = "一级分类",index = 0)//表示对应excel表格里面的第一列数据
    private String subjectLevelOneName;//一级课程名称

    @ExcelProperty(value = "二级分类",index = 1)//表示对应excel表格里面的第二列数据
    private String subjectLevelTwoName;//二级课程名称

}
