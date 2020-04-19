package com.atguigu.eduservice.client.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月20日
 */
@Component
@Slf4j
public class VodDegradeFeignClient implements VodClient {

    //出现了调用其它微服务超时了或者其它微服务宕机了等“错误”情况就会执行这个方法
    @Override
    public R removeVideo(String videoSourceIds) {
        log.info("VodDegradeFeignClient's removeVideo method...");
        return R.error().message("删除云端视频失败(⊙︿⊙)(⊙︿⊙)(⊙︿⊙)");
    }

}
