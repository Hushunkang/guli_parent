package com.atguigu.eduservice.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月17日
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String status;

}
