package com.hww.sns.common.manager;

import java.util.List;

import com.hww.base.common.manager.IBaseEntityMng;
import com.hww.sns.common.dao.SnsReportDao;
import com.hww.sns.common.entity.SnsReport;

public interface SnsReportMng extends
    IBaseEntityMng<Long, SnsReport, SnsReportDao>{
	
	
	public void saveReport(SnsReport report);
	public List<SnsReport> queryList();


}
