package com.atguigu.ucenterservice.controller;


import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.service.MemberService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hskBeginner
 * @since 2020-04-24
 */
@Api(description = "用户中心API")
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class MemberController {

    @Autowired
    private MemberService memberService;

    //注册会员


    //会员登录
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@ApiParam(name = "member", value = "会员信息") @RequestBody Member member){
        //返回jwt生成的token字符串
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

}
