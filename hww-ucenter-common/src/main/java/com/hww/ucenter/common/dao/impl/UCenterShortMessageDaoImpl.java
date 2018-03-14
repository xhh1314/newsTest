package com.hww.ucenter.common.dao.impl;

import com.hww.ucenter.common.dao.UCenterShortMessageDao;
import com.hww.ucenter.common.dao.impl.jpa.UCenterShortMessageJpa;
import com.hww.ucenter.common.entity.UCenterShortMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UCenterShortMessageDaoImpl implements UCenterShortMessageDao {

    /**
     * 直接调用JPA框架，不使用二次封装的框架
     */
    @Autowired
    private UCenterShortMessageJpa UCenterShortMessageJpa;

    @Override
    public UCenterShortMessage saveShortMessage(UCenterShortMessage UCenterShortMessage) {

        return UCenterShortMessageJpa.save(UCenterShortMessage);
    }

    @Override
    public UCenterShortMessage updateShortMessage(UCenterShortMessage UCenterShortMessage) {
        return UCenterShortMessageJpa.save(UCenterShortMessage);
    }

    @Override
    public void deleteShortMessage(UCenterShortMessage UCenterShortMessage) {
        UCenterShortMessageJpa.delete(UCenterShortMessage);
    }

    @Override
    public Integer getCountSendMessageCurrentDay(String phoneNumber) {
        return UCenterShortMessageJpa.getCountSendMessageCurrentDay(phoneNumber);
    }

    @Override
    public List<UCenterShortMessage> listShortMessageByPhoneNumber(String phoneNumber) {
        return UCenterShortMessageJpa.getShortMessageByPhoneNumber(phoneNumber);
    }

    @Override
    public List<UCenterShortMessage> listShortMessageByPhoneNumberWhinMinutes(String phoneNumber, Integer minute) {
        return UCenterShortMessageJpa.listShortMessageByPhoneNumberWhinThrityMinutes(phoneNumber, minute.toString());
    }
}
