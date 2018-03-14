package com.hww.ucenter.common.dao.impl.jpa;

import com.hww.ucenter.common.entity.UCenterShortMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UCenterShortMessageJpa extends JpaRepository<UCenterShortMessage, Integer> {
    @Query(value = "select count(*) from UCenterShortMessage where phoneNumber=?1 and TO_DAYS(NOW())-TO_DAYS(sendMessageTime)<1")
	Integer getCountSendMessageCurrentDay(String phoneNumber);

    @Query(value = "from UCenterShortMessage where phoneNumber=?1")
    List<UCenterShortMessage> getShortMessageByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM ucenter_short_message WHERE phone_number=?1 AND DATE_ADD(NOW(), INTERVAL -?2 MINUTE) < send_message_time ", nativeQuery = true)
    List<UCenterShortMessage> listShortMessageByPhoneNumberWhinThrityMinutes(String phoneNumber, String minute);
}
