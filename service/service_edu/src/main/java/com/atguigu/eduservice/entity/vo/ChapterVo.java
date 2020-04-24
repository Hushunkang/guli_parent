package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月15日
 */
@ApiModel(value="课程章节", description="课程章节")
@Data
public class ChapterVo {

    @ApiModelProperty(value = "课程章节ID")
    private String id;

    @ApiModelProperty(value = "课程章节名称")
    private String title;

    //模拟表示章节下面可以有多个小节
    @ApiModelProperty(value = "一个课程章节下面的课程小节")
    private List<VideoVo> children;

}
