package com.atguigu.smsservice.service;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
public interface SmsService {

    /**
     * 发送手机短信验证码（阿里云短信服务，模板类型为验证码）
     * @param phoneNumber
     */
    void sendSmsCode(String phoneNumber);

}
