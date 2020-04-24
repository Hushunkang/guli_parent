package com.atguigu.smsservice.service;

/**
 * @author hskBeginner Email：2752962035@qq.com
 * @version 1.0
 * @description
 * @create 2020年04月24日
 */
public interface SmsService {

    /**
     *
     * @param phoneNumber
     */
    void sendSmsCode(String phoneNumber);

}
