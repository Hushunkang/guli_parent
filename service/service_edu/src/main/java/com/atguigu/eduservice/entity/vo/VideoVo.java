package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月15日
 */
@Data
public class VideoVo {

    @ApiModelProperty(value = "课程小节ID")
    private String id;

    @ApiModelProperty(value = "课程小节名称")
    private String title;

}
