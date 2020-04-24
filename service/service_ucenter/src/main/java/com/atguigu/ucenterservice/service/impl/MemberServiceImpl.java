package com.atguigu.ucenterservice.service.impl;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.mapper.MemberMapper;
import com.atguigu.ucenterservice.service.MemberService;
import com.atguigu.util.JwtUtils;
import com.atguigu.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-24
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public String login(Member member) {
        //获取登录会员的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //判断手机号是否为空
        if (StringUtils.isEmpty(mobile)) {
            throw new GuliException(ResultCode.ERROR,"登录失败，手机号不能为空(⊙︿⊙)");
        }

        //判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCode.ERROR,"登录失败，密码不能为空(⊙︿⊙)");
        }

        //判断手机号有无被注册
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(ResultCode.ERROR,"登录失败，此手机号未被注册(⊙︿⊙)");
        }

        //判断密码
        if (!password.equals(mobileMember.getPassword())) {
            throw new GuliException(ResultCode.ERROR,"登录失败，密码不匹配(⊙︿⊙)");
        }

        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(ResultCode.ERROR,"登录失败，您被禁用了(⊙︿⊙)");
        }

        //为了实现单点登录，登录成功后需要返回jwt生成的token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

}
