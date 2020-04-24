package com.atguigu.smsservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.baseservice.exception.GuliException;
import com.atguigu.smsservice.service.SmsService;
import com.atguigu.smsservice.util.ConstantSmsUtils;
import com.atguigu.smsservice.util.RandomUtils;
import com.atguigu.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;//spring boot底层整合了redis，自动配置类中通过了一定的条件然后向spring ioc容器里面注册了redis模板组件

    @Override
    public void sendSmsCode(String phoneNumber) {
        //会员注册，获取验证码
        boolean alreadyRegister = isAlreadyRegister(phoneNumber);
        if (alreadyRegister) {
            throw new GuliException(ResultCode.ERROR,"获取验证码失败，此手机号已被注册(⊙︿⊙)");
        }
        //从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) {
            throw new GuliException(ResultCode.ERROR,"手速太快了，请您稍后再试(⊙︿⊙)");
        }

        //从redis获取不到验证码，调用阿里云短信服务发送验证码
        code = RandomUtils.getFourBitRandom();//应用内部自己生成的手机短信验证码（注意：根据项目具体的业务需求，有时候需要建立一张验证码表做一些的事情）
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);//key必须是code，因为阿里云openapi里面要求的请求报文的参数名相关内容必须是这个，详情参照阿里云openapi相关文档
        //调用service发送手机短信验证码
        boolean isSend = sendSmsCode(param,phoneNumber);
        if(isSend) {//发送手机短信验证码成功，将发送成功的验证码放到redis里面
            //设置redis键值对的有效时间
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);//验证码5分钟内有效
        } else {
            throw new GuliException(ResultCode.ERROR,"抱歉，验证码发送失败，请您稍后再试(⊙︿⊙)");
        }
    }

    /**
     * 判断手机号是否已经被注册
     * @param phoneNumber
     * @return
     */
    private boolean isAlreadyRegister(String phoneNumber){
        //todo 此处应该调用用户中心暴露出来的接口，即远程调用给我返回这个手机号是否已经被注册，后面完善，先写死为false
        return false;
    }

    /**
     * 发送手机短信验证码
     * @param param
     * @param phoneNumber
     * @return 如果发送手机短信验证码成功返回true，否则返回false
     */
    private boolean sendSmsCode(Map<String, Object> param, String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou", ConstantSmsUtils.ACCESS_KEY_ID, ConstantSmsUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");//表示调用阿里云openapi中的哪一个接口，此处为SendSms
        request.putQueryParameter("RegionId", "cn-hangzhou");

        //设置发送手机短信验证码相关的参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);//手机号
        request.putQueryParameter("SignName", "谷粒学院在线教育网站");//阿里云短信服务中申请的签名的签名名称
        request.putQueryParameter("TemplateCode", "SMS_189030146");//阿里云短信服务中申请的模板的模板CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码数据，接口文档要求转换为json数据格式

        try {
            //最终发送手机短信验证码
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSON.parseObject(response.getData());
            if ("OK".equals(jsonObject.get("Code")))//表明发送手机短信验证码成功，代码为什么这么写，要去看阿里云openapi相关文档
                return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
