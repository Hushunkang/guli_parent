package com.atguigu.ucenterservice.controller;

import com.atguigu.ucenterservice.util.ConstantUserCenterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月26日
 */
@Api(description = "微信API")
@Controller
@RequestMapping("/ucenterservice/wx")
@CrossOrigin
public class WeChatApiController {

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

}
