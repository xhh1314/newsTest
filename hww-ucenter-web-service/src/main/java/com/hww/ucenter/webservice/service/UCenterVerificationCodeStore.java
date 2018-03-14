package com.hww.ucenter.webservice.service;

import com.hww.ucenter.common.entity.UCenterShortMessage;

/**
 * 手机验证码存储接口
 *
 * 把用户发的验证码存起来，如果验证码没有过期，直接返回原验证码，不再重复发送验证码；取出验证码的时候，如果验证码过期，则返回null；一天只能最多发送三条验证码，节约成本
 */
public interface UCenterVerificationCodeStore {

    /**
     * 从缓存中根据手机号取出有效期内的验证码
     * @param phoneNumber
     * @return 验证码
     */
    UCenterShortMessage getCode(String phoneNumber);

    /**
     * 看是否超过发送次数限制次数
     * @param phoneNumber
     * @return 返回fasle表示没有超过限制
     */
    boolean getExceedSendCountLimit(String phoneNumber);

    /**
     * 把验证码存储在缓存中
     * @param phoneNumber
     * @return
     */
    String saveCode(String phoneNumber,String code);

    /**
     * 把手机号保存到重复库中
     * @param phoneNumber
     * @return
     */
    String savePhoneNumberIntoRepetition(String phoneNumber);

    void deleteCode(UCenterShortMessage oldCode);
}
