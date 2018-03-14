package com.hww.sns.webservice.service;

import com.hww.sns.common.entity.SnsTopicEsSyncFail;

public interface SnstopicEsSyncFailService {

	void save(SnsTopicEsSyncFail snsTopicEsSyncFail);
	
	
	void doAync();
	
	

	
}
