package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.service.BannerService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台系统，banner展示
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-22
 */
@Api(description = "前台系统banner展示API")
@RestController
@RequestMapping("/cmsservice/frontbanner")
@CrossOrigin
public class FrontBannerController {

    @Autowired
    private BannerService bannerService;

    //查询出最近更新的四个banner（若不足4个，查询所有）
    @ApiOperation(value = "查询出最近更新的四个banner（若不足4个，查询所有）")
    @GetMapping("getBanners")
    public R getBanners(){
        List<Banner> list = bannerService.getBanners();
        return R.ok().data("list",list);
    }

}
