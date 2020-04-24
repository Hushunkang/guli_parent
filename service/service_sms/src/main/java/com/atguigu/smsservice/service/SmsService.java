package com.atguigu.smsservice.service;

import java.util.Map;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
public interface SmsService {

    /**
     * 发送手机短信验证码
     * @param param
     * @param phoneNumber
     * @return 如果发送手机短信验证码成功返回true，否则返回false
     */
    boolean sendSmsCode(Map<String, Object> param, String phoneNumber);

}
