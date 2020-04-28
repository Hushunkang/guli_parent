package com.atguigu.smsservice.controller;

import com.atguigu.smsservice.service.SmsService;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
@Api(description = "阿里云短信服务API")
@RestController
@RequestMapping("/smsservice/sms")
@CrossOrigin
public class SmsController {

    @Autowired
    private SmsService smsService;

    //发送手机短信验证码（阿里云短信服务，模板类型为验证码）
    @ApiOperation(value = "发送手机短信验证码")
    @GetMapping("sendSms/{phoneNumber}")
    public R sendSms(@ApiParam(name = "phoneNumber", value = "手机号码") @PathVariable("phoneNumber") String phoneNumber) {
        smsService.sendSmsCode(phoneNumber);
        return R.ok().message("验证码成功发送，请您注意及时查收(*￣︶￣)");
    }

}
