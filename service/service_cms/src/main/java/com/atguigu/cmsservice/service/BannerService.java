package com.atguigu.cmsservice.service;

import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.entity.query.BannerQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-22
 */
public interface BannerService extends IService<Banner> {

    /**
     * 多条件组合查询带分页效果的banner列表
     * @param current 当前页
     * @param size 每页记录数
     * @param bannerQuery 多个查询条件
     * @return
     */
    Page<Banner> pageBanner(Long current, Long size, BannerQuery bannerQuery);

    /**
     * 查询出最近更新的四个banner（若不足4个，查询所有）
     * @return
     */
    List<Banner> getBanners();

}
