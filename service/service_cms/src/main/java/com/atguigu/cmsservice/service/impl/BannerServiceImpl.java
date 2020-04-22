package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.entity.query.BannerQuery;
import com.atguigu.cmsservice.mapper.BannerMapper;
import com.atguigu.cmsservice.service.BannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-22
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public Page<Banner> pageBanner(Long current, Long size, BannerQuery bannerQuery) {
        Page<Banner> pageBanner = new Page<>(current,size);

        String title = bannerQuery.getTitle();
        String begin = bannerQuery.getBegin();
        String end = bannerQuery.getEnd();

        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(pageBanner,wrapper);

        return pageBanner;
    }

    @Override
    public List<Banner> getBanners() {
        //查询出最近更新的四个banner（若不足4个，查询所有）
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");
        //最多查询4条记录
        wrapper.last("limit 4");//拼接sql
        List<Banner> banners = baseMapper.selectList(wrapper);
        return banners;
    }

}
