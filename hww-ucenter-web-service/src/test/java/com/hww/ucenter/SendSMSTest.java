//package com.hww.ucenter;
//
//import com.hww.ucenter.common.dao.UcenterShortMessageDao;
//import com.hww.ucenter.common.entity.UcenterShortMessage;
//import com.hww.ucenter.webservice.service.UCenterSendSMS;
//import com.hww.ucenter.webservice.service.UCenterVerificationCodeStore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = UcenterWebServiceApplication.class)
//public class SendSMSTest {
//	@Autowired
//	private UCenterSendSMS ucenterSendSMS;
//
//	@Autowired
//	private UcenterShortMessageDao ucenterShortMessageDao;
//
//	@Autowired
//	private UCenterVerificationCodeStore ucenterVerificationCodeStore;
//
//	@Test
//	public void sendMessage() {
//		ucenterSendSMS.sendMessage("86", "15901015121", "4455");
//	}
//
//	@Test
//	public void InsertShortMessage(){
//		UcenterShortMessage ucenterShortMessage=new UcenterShortMessage();
//		ucenterShortMessage.setId(new Long(1526163163));
//		ucenterShortMessage.setCode("0031");
//		ucenterShortMessage.setPhoneNumber("15901015121");
//		ucenterShortMessage.setSendMessageTime(new Date());
//		ucenterShortMessageDao.saveShortMessage(ucenterShortMessage);
//	}
//
//	@Test
//	public void findTest(){
//		String phoneNumber="15901015121";
//		Integer count=ucenterShortMessageDao.getCountSendMessageCurrentDay(phoneNumber);
//		System.out.println("数量是："+count);
//		List<UcenterShortMessage> ucenterShortMessagelist=ucenterShortMessageDao.listShortMessageByPhoneNumber(phoneNumber);
//		List<UcenterShortMessage> ucenterShortMessages=ucenterShortMessageDao.listShortMessageByPhoneNumberWhinMinutes(phoneNumber,30);
//	}
//	@Test
//	public void getRecentlyCode(){
//		String code=ucenterVerificationCodeStore.getCode("15901015121");
//	}
//
//
//}
