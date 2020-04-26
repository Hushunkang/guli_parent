package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-24
 */
public interface MemberService extends IService<Member> {

    /**
     * 注册会员
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 会员登录
     * @return
     * @param member
     */
    String login(Member member);

    /**
     * 根据调用微信提供的接口返回的openId去找会员信息
     * @param openId 唯一标识一个微信账号
     * @return
     */
    Member getMemberByOpenId(String openId);

}
