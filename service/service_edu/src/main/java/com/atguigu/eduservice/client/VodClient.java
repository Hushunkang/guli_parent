package com.atguigu.eduservice.client;

import com.atguigu.util.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月19日
 */
@Component
@FeignClient("service-vod")//service-vod为要被调用的微服务名称
public interface VodClient {

    //根据阿里云视频点播服务为每一个视频生成的视频ID来删除云端视频
    @ApiOperation(value = "单个删除云端视频")
    @DeleteMapping("/vodservice/video/removeVideo/{videoSourceIds}")
    R removeVideo(@ApiParam(name = "videoSourceIds", value = "云端视频IDS", required = true) @PathVariable("videoSourceIds") String videoSourceIds);

}
