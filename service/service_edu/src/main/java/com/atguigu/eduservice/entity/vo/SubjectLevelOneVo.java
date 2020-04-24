package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * 用于生成树结构的数据模型，一级分类
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月14日
 */
@ApiModel(value="一级课程分类", description="一级课程分类")
@Data
public class SubjectLevelOneVo {

    @ApiModelProperty(value = "一级课程分类ID")
    private String id;

    @ApiModelProperty(value = "一级课程分类名称")
    private String title;

    //模拟表示一级课程分类下面可以有多个二级课程分类
    @ApiModelProperty(value = "一个一级课程分类下面的二级课程分类")
    private List<SubjectLevelTwoVo> children;

}
