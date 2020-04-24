package com.atguigu.smsservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.smsservice.service.SmsService;
import com.atguigu.smsservice.util.ConstantSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Override
    public boolean sendSmsCode(Map<String, Object> param, String phoneNumber) {
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
