package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.Member;
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
     * 会员登录
     * @return
     * @param member
     */
    String login(Member member);

}
