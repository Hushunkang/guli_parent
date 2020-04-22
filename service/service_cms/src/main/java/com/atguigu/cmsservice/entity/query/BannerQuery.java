package com.atguigu.cmsservice.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 首页banner表
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-22
 */
@Data
public class BannerQuery {

    @ApiModelProperty(value = "banner标题，模糊查询")
    private String title;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行数据类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;

}
