package com.hww.ucenter.webservice.service.impl;

import com.hww.base.util.SnowFlake;
import com.hww.ucenter.common.dao.UCenterShortMessageDao;
import com.hww.ucenter.common.entity.UCenterShortMessage;
import com.hww.ucenter.webservice.service.UCenterVerificationCodeStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 用数据库存
 */
@Service
public class UCenterVerificationCodeStoreImpl implements UCenterVerificationCodeStore {

	@Autowired
	private UCenterShortMessageDao UCenterShortMessageDao;

	// TODO 应该从配置文件读取
    private String host = "127.0.0.1";
	private String port = "6379";
	private final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	private final JedisPool pool = new JedisPool(jedisPoolConfig, host, Integer.parseInt(port));

	/**
	 *过期时间30分钟
	 */
	private final Integer codeKeyExpirationTime = 30;

	/**
	 *限制一天发送几条验证码 3条
	 */
	private final Integer repetitionNumber = 3;

	@Override
    public UCenterShortMessage getCode(String phoneNumber) {
        List<UCenterShortMessage> UCenterShortMessages = UCenterShortMessageDao
				.listShortMessageByPhoneNumberWhinMinutes(phoneNumber, codeKeyExpirationTime);
        if (UCenterShortMessages != null && UCenterShortMessages.size() > 0) {
            Collections.sort(UCenterShortMessages);
            return UCenterShortMessages.get(0);
		}
		return null;
	}

	@Override
	public boolean getExceedSendCountLimit(String phoneNumber) {
		Integer count = UCenterShortMessageDao.getCountSendMessageCurrentDay(phoneNumber);
        return count != null && count >= repetitionNumber;
	}

	@Override
	public String saveCode(String phoneNumber, String code) {
        UCenterShortMessage UCenterShortMessage = new UCenterShortMessage();
        UCenterShortMessage.setSendMessageTime(new Date());
        UCenterShortMessage.setPhoneNumber(phoneNumber);
        UCenterShortMessage.setCode(code);
//		SnowFlake snowFlake = new SnowFlake(1, 1);
//        UCenterShortMessage.setId(snowFlake.nextId());
        UCenterShortMessage newUCenterShortMessage = UCenterShortMessageDao.saveShortMessage(UCenterShortMessage);
        return newUCenterShortMessage != null ? newUCenterShortMessage.getCode() : null;
	}

	@Override
	public String savePhoneNumberIntoRepetition(String phoneNumber) {
		throw new RuntimeException("接口未实现!");
	}

    @Override
    public void deleteCode(UCenterShortMessage oldCode) {
        UCenterShortMessageDao.deleteShortMessage(oldCode);
    }
}
