package com.atguigu.ucenterservice.service.impl;

import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.mapper.MemberMapper;
import com.atguigu.ucenterservice.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
