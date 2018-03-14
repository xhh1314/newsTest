package com.hww.sns.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;
import com.hww.sns.common.dao.SnsReportDao;
import com.hww.sns.common.entity.SnsReport;
import com.hww.sns.common.manager.SnsReportMng;
@Service("snsReportMng")
public class SnsReportMngImpl  extends
     BaseEntityMngImpl<Long, SnsReport, SnsReportDao>
       implements
           SnsReportMng{
	
	 @Autowired
	    public void setSnsPostDao(SnsReportDao snsReportDao) {
	        super.setEntityDao(snsReportDao);
	        this.snsReportDao = snsReportDao;
	    }

	    @Autowired SnsReportDao snsReportDao;

	@Override
	public void saveReport(SnsReport report) {
		snsReportDao.save(report);
		
	}

	@Override
	public List<SnsReport> queryList() {
		Finder f=Finder.create(" from SnsReport");
		return (List<SnsReport>) snsReportDao.find(f);
	}
	
	
	
	
	

}
