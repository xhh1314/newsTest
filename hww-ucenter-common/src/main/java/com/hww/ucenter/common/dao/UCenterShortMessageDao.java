package com.hww.ucenter.common.dao;

import com.hww.ucenter.common.entity.UCenterShortMessage;

import java.util.List;

/**
 * 操作shortMessage的dao
 */
public interface UCenterShortMessageDao {

    UCenterShortMessage saveShortMessage(UCenterShortMessage UCenterShortMessage);

    UCenterShortMessage updateShortMessage(UCenterShortMessage UCenterShortMessage);

    void deleteShortMessage(UCenterShortMessage UCenterShortMessage);

    /**
     * 查找当天发送的验证码次数
     * @param phoneNumber
     * @return
     */
    Integer getCountSendMessageCurrentDay(String phoneNumber);

    List<UCenterShortMessage> listShortMessageByPhoneNumber(String phoneNumber);


    /**
     * 查询 xx 分钟以内的验证码
     * @param phoneNumber
     * @param minute
     * @return
     */
    List<UCenterShortMessage> listShortMessageByPhoneNumberWhinMinutes(String phoneNumber, Integer minute);



}
