package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.entity.query.BannerQuery;
import com.atguigu.cmsservice.service.BannerService;
import com.atguigu.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台系统，banner管理
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-22
 */
@Api(description = "后台banner管理API")
@RestController
@RequestMapping("/cmsservice/backbanner")
@CrossOrigin
public class BannerBackController {

    @Autowired
    private BannerService bannerService;

    //分页查询banner列表
    @ApiOperation(value = "多条件组合查询带分页效果的banner列表")
    @GetMapping("pageBanner/{current}/{size}")
    public R pageBanner(@ApiParam(name = "current", value = "当前页") @PathVariable Long current,
                        @ApiParam(name = "size", value = "每页记录数") @PathVariable Long size,
                        @ApiParam(name = "bannerQuery", value = "多个查询条件") @RequestBody(required = false) BannerQuery bannerQuery) {
        Page<Banner> pageBanner = bannerService.pageBanner(current,size,bannerQuery);

        long total = pageBanner.getTotal();
        List<Banner> records = pageBanner.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    //添加banner
    //添加讲师
    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public R addBanner(@ApiParam(name = "banner", value = "banner信息") @RequestBody Banner banner){
        boolean flag = bannerService.save(banner);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除banner
    @ApiOperation(value = "删除banner")
    @DeleteMapping("removeBanner/{bannerId}")
    public R removeBanner(@ApiParam(name = "bannerId", value = "bannerID", required = true)
                           @PathVariable String bannerId){
        boolean flag = bannerService.removeById(bannerId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据bannerID查询banner
    @ApiOperation(value = "根据bannerID查询banner")
    @GetMapping("getBannerById/{bannerId}")
    public R getBannerById(@ApiParam(name = "bannerId", value = "bannerID", required = true) @PathVariable String bannerId){
        Banner banner = bannerService.getById(bannerId);
        return R.ok().data("banner",banner);
    }

    //修改banner
    @ApiOperation(value = "修改banner")
    @PostMapping("updateBanner")
    public R updateBanner(@ApiParam(name = "banner", value = "banner信息") @RequestBody Banner banner){
        boolean flag = bannerService.updateById(banner);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}
