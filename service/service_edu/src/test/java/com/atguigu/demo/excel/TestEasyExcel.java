package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.atguigu.demo.listener.ExcelListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月08日 02时08分42秒
 */
public class TestEasyExcel {

    @Test
    public void write(){
        //设置给Excel文件写到哪儿去和文件名称
        String filePath = "C:\\Users\\27529\\Desktop\\essential\\grade8class6student.xlsx";
        EasyExcel.write(filePath,Student.class).sheet("学生列表").doWrite(data());
    }

    private List<Student> data(){
        List<Student> students = new ArrayList<>();
        for(int i = 10;i > 0;i--){
            Student student = new Student();
            student.setStudentNo(i);
            student.setStudentName("马云" + "_" + i);
            students.add(student);
        }
        return students;
    }

    @Test
    public void read(){
        String filePath = "C:\\Users\\27529\\Desktop\\essential\\subject.xlsx";

        EasyExcel.read(filePath,Subject.class,new ExcelListener()).sheet().doRead();
    }

}
