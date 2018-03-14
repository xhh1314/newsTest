package com.hww.sns.webservice.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.sns.common.entity.SnsReport;
import com.hww.sns.common.manager.SnsReportMng;
import com.hww.sns.webservice.service.SnsReportService;
@Service("snsReportService")
@Transactional
public class SnsReportServiceImpl implements SnsReportService{

	@Autowired
	private SnsReportMng snsReportMng;
	
	
	@Override
	public void saveReport(SnsReport report) {
		snsReportMng.saveReport(report);
		
	}

	
}
