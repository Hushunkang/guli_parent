package com.atguigu.smsservice.controller;

import com.atguigu.smsservice.service.SmsService;
import com.atguigu.smsservice.util.RandomUtils;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate<String,String> redisTemplate;//spring boot底层整合了redis，自动配置类中通过了一定的条件然后向spring ioc容器里面注册了redis模板组件

    //发送手机短信验证码（阿里云短信服务，模板类型为验证码）
    @ApiOperation(value = "发送手机短信验证码")
    @GetMapping("sendSms/{phoneNumber}")
    public R sendSms(@ApiParam(name = "phoneNumber", value = "手机号码") @PathVariable String phoneNumber) {
        //从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //从redis获取不到验证码，调用阿里云短信服务发送验证码
        code = RandomUtils.getFourBitRandom();//应用内部自己生成的手机短信验证码（注意：根据项目具体的业务需求，有时候需要建立一张验证码表做一些的事情）
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);//key必须是code，因为阿里云openapi里面要求的请求报文的参数名相关内容必须是这个，详情参照阿里云openapi相关文档
        //调用service发送手机短信验证码
        boolean isSend = smsService.sendSmsCode(param,phoneNumber);
        if(isSend) {//发送手机短信验证码成功，将发送成功的验证码放到redis里面
            //设置redis键值对的有效时间
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);//验证码5分钟内有效
            return R.ok();
        } else {
            return R.error().message("抱歉，验证码发送失败，请稍后再试(⊙︿⊙)");
        }
    }

}
