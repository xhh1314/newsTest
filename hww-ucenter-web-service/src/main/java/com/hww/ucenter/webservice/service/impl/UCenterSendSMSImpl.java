package com.hww.ucenter.webservice.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.hww.ucenter.webservice.service.UCenterSendSMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class UCenterSendSMSImpl implements UCenterSendSMS {

    private static final Logger log = LoggerFactory.getLogger(UCenterSendSMSImpl.class);

	// TODO 属性的值应该写到配置文件里

	/**
	 * 买服务给的公司码
	 */
	private int appid = 1400051549;
	/**
	 *腾讯给的key
	 */
	private String appKey = "661aaa4c65d600251a4e895a9621f8c6";
	/**
	 *模板id 到腾讯云查的
	 */
	private Integer templId = 64855;
	/**
	 * 过期时间
	 */
	private String expirationTime = "30";

	private SmsSingleSender sender;

	@PostConstruct
	public void init() {
		try {
			sender = new SmsSingleSender(appid, appKey);
		} catch (Exception e) {
			log.error("初始化短信发送实例错误!");
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean sendMessage(String nationCode, String phoneNumber, String code) {
		try {
			// 短信模板：{1}为您的注册验证码，请于{2}分钟内填写。如非本人操作，请忽略本短信。
			ArrayList<String> params = new ArrayList<String>();
			// 验证码为{}
			params.add(code);
			// 30分钟内过期
			params.add(expirationTime);
			SmsSingleSenderResult result = sender.sendWithParam(nationCode, phoneNumber, templId, params, "", "", "");
			if (result.result != 0)
				return false;
			log.info("向手机:{}发送了验证码:{}", phoneNumber, code);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("向手机号:{}发送短信验证码:{}发生异常,{}", phoneNumber, code, e);
			return false;
		}

	}
}

/*
 * { "tel": { //如需使用国际电话号码通用格式，如："+8613788888888" ，请使用sendisms接口见下注
 * "nationcode": "86", //国家码 "mobile": "13788888888" //手机号码 }, "sign": "腾讯云",
 * //短信签名，如果使用默认签名，该字段可缺省 "tpl_id": 19, //业务在控制台审核通过的模板ID
 * //假定这个模板为：您的{1}是{2}，请于{3}分钟内填写。如非本人操作，请忽略本短信。 "params": [ "验证码", "1234", "4"
 * ], //参数，分别对应上面假定模板的{1}，{2}，{3} "sig":
 * "ecab4881ee80ad3d76bb1da68387428ca752eb885e52621a3129dcf4d9bc4fd4",
 * //app凭证，具体计算方式见下注 "time": 1457336869, //unix时间戳，请求发起时间，如果和系统时间相差超过10分钟则会返回失败
 * "extend": "", //通道扩展码，可选字段，默认没有开通(需要填空)。
 * //在短信回复场景中，腾讯server会原样返回，开发者可依此区分是哪种类型的回复 "ext": ""
 * //用户的session内容，腾讯server回包中会原样返回，可选字段，不需要就填空。 }
 */
