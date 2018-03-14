package com.hww.sns.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hww.sns.common.dao.SnsReportDao;
import com.hww.sns.common.entity.SnsReport;
@Repository("snsReportDao")
public class SnsReportDaoImpl extends LocalDataBaseDaoImpl<Long, SnsReport> implements SnsReportDao {

}
