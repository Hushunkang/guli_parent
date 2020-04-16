package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月16日
 */
@Data
public class CoursePublishVo {

    //课程ID
    private String id;
    //课程名称
    private String title;
    //课程封面
    private String cover;
    //课时数
    private Integer lessonNum;
    //一级课程分类名称
    private String subjectLevelOne;
    //二级课程分类名称
    private String subjectLevelTwo;
    //讲师名称
    private String teacherName;
    //课程价格
    private String price;//只用于读，不用于精准的算术运算，不需要使用BigDecimal数据类型

}
