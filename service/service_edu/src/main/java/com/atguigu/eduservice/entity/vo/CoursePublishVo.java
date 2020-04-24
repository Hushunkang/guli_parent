package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月16日
 */
@ApiModel(value="课程发布对象", description="课程发布对象")
@Data
public class CoursePublishVo {

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(value = "课时数")
    private Integer lessonNum;

    @ApiModelProperty(value = "一级课程分类名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "二级课程分类名称")
    private String subjectLevelTwo;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "课程价格")
    private String price;//只用于读，不用于精准的算术运算，不需要使用BigDecimal数据类型

}
