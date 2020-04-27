package com.atguigu.ucenterservice.controller;

import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.service.MemberService;
import com.atguigu.ucenterservice.util.ConstantUserCenterUtils;
import com.atguigu.ucenterservice.util.HttpClientUtils;
import com.atguigu.util.JwtUtils;
import com.atguigu.util.ResultCode;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月26日
 */
@Api(description = "微信API")
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
@Slf4j
public class WeChatApiController {

    @Autowired
    private MemberService memberService;

    //获取微信扫码登录的二维码
    @ApiOperation(value = "获取微信扫码登录的二维码")
    @GetMapping("getWeChatCode")
    public String getWeChatCode(){
        //微信开放平台授权baseUrl，%s相当于?，代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //使用URLEncoder对链接进行处理
        String redirectUrl = ConstantUserCenterUtils.REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantUserCenterUtils.APP_ID,
                redirectUrl,
                "atguigu"
        );

        return "redirect:" + url;
    }

    //扫描微信登录的二维码后，确认登录，最终调用的方法
    @ApiOperation(value = "回调方法")
    @GetMapping("callback")
    public String callback(String code, String state/*, HttpServletResponse response*/){
        log.info("code为：" + code);//授权临时票据
        log.info("state为：" + state);//用于保持请求和回调的状态，授权请求后原样带回给第三方

        try {
            //通过code获取access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //设置%s里面值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantUserCenterUtils.APP_ID,
                    ConstantUserCenterUtils.APP_SECRET,
                    code
            );

            //httpclient技术（很古老的一种接口调用技术）调用微信提供的接口
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);//由于请求参数是拼接在url后面的，因此选择get请求方式
            log.info("accessTokenInfo为：" + accessTokenInfo);

            //解析json字符串（提醒下自己，如果大面积需要解析json，建议写一个解析json的工具类，这是一种思想，其它有类似的需要也是类似的操作）
            Gson gson = new Gson();
            HashMap result = gson.fromJson(accessTokenInfo, HashMap.class);

            //拿到接口响应报文里面的access_token和openid
            String accessToken = (String) result.get("access_token");
//            result.get("expires_in");
//            result.get("refresh_token");
            String openId = (String) result.get("openid");
//            result.get("scope");
//            result.get("unionid");

            //根据调用微信提供的接口返回的openId去找会员信息
            Member member = memberService.getMemberByOpenId(openId);
            if (member == null) {
                //通过access_token再去调用微信提供的接口，最终可以拿到扫码人的相关数据
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                //设置%s里面值
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openId
                );

                //httpclient技术（很古老的一种接口调用技术）调用微信提供的接口
                String userInfo = HttpClientUtils.get(userInfoUrl);
                log.info("userInfo为：" + userInfo);

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);

                //拿到接口响应报文里面的nickname和headimgurl
                String nickName = (String)userInfoMap.get("nickname");//扫码人昵称
                String headImgUrl = (String)userInfoMap.get("headimgurl");//扫码人头像地址

                member = new Member();
                member.setOpenId(openId);
                member.setNickname(nickName);
                member.setAvatar(headImgUrl);
                memberService.save(member);
            }

            //根据member信息，利用jwt技术生成存有会员信息的token
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            //服务端设置一个cookie，在响应头里面设置cookie，通知客户端保存这个cookie
//            response.setHeader("Set-Cookie","k1=v1; Domain=localhost; Path=/; HttpOnly");

            //回到谷粒学院前台系统首页
            return "redirect:http://localhost:3000?token=" + token;//通过请求的路径传递token到谷粒学院前台系统首页
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(ResultCode.ERROR,"登录失败(⊙︿⊙)");
        }
    }

}
