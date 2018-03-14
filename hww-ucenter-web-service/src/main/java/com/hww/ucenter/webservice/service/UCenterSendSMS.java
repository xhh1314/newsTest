package com.hww.ucenter.webservice.service;

/**
 * 发送短信的接口
 */
public interface UCenterSendSMS {

    boolean sendMessage(String countryCode,String phoneNumber,String code);
}
