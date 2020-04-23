package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.Banner;
import com.atguigu.cmsservice.entity.query.BannerQuery;
import com.atguigu.cmsservice.mapper.BannerMapper;
import com.atguigu.cmsservice.service.BannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "banner", key = "'getBanners'")//value::key组合形成了redis键值对数据库当中的key，此处为banner::getBanners
    //为什么key是这样的，尤其是中间的那两个::，请看org.springframework.data.redis.cache.CacheKeyPrefix源码
    //@Cacheable注解一般用于查询方法上面
    //此场景下的大致含义是将方法的返回值放到redis缓存里面去，key为banner::getBanners，value为方法的返回值
    //用户每次请求这个方法的时候，会去redis中根据key找数据
    //找到，就返回redis中的数据到前端去（控制台没打印出sql）
    //没有找到，还要调用一下这个方法，底层通过jdbc的方式访问mysql，最终拿到数据返回给前端（控制台有打印出sql）
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
