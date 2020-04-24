package com.atguigu.smsservice.controller;

import com.atguigu.smsservice.service.SmsService;
import com.atguigu.smsservice.util.RandomUtils;
import com.atguigu.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private RedisTemplate redisTemplate;

    //发送手机短信验证码（阿里云短信服务，模板类型为验证码）
    @ApiOperation(value = "发送手机短信验证码")
    @GetMapping("send/{phoneNumber}")
    public R sendMsm(@PathVariable String phoneNumber) {
        //从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //2 如果redis获取 不到，进行阿里云发送
        //生成随机值，传递阿里云进行发送
        code = RandomUtils.getFourBitRandom();//应用内部自己生成的手机短信验证码（注意：根据项目具体的业务需求，有时候需要建立一张验证码表做一些的事情）
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);//key必须是code，因为阿里云openapi里面要求的请求报文的参数名相关内容必须是这个，详情参照阿里云openapi相关文档
        //调用service发送短信的方法
        boolean isSend = smsService.sendSmsCode(param,phoneNumber);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }

}
