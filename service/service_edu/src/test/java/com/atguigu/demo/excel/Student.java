package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 *
 * easyExcel写操作简单的示例代码
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日
 */
@Data
public class Student {

    @ExcelProperty("学生编号")//设置excel表头
    private Integer studentNo;

    @ExcelProperty("学生姓名")//设置excel表头
    private String studentName;

}
