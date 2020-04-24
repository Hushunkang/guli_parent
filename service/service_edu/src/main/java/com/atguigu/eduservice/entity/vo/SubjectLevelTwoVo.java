package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 用于生成树结构的数据模型，二级分类
 *
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月14日
 */
@ApiModel(value="二级课程分类", description="二级课程分类")
@Data
public class SubjectLevelTwoVo {

    @ApiModelProperty(value = "二级课程分类ID")
    private String id;

    @ApiModelProperty(value = "二级课程分类名称")
    private String title;

}
