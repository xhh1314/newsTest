package com.hww.app.admin.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hww.app.admin.service.AppVersionService;
import com.hww.app.common.entity.AppVersion;
import com.hww.app.common.manager.AppVersionMng;
import com.hww.app.common.vo.AppVersionVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
@Service("appVersionService")
@Transactional
public class AppVersionServiceImpl implements AppVersionService{

	@Autowired private AppVersionMng appVersionMng;
	public Pagination appVersionlist(AppVersionVo appVersionVo) {
		Finder hql=Finder.create("from AppVersion");
		hql.append(" where status=1  order by createTime desc ");
		Pagination p=	appVersionMng.find(hql,appVersionVo.getPageNo(), appVersionVo.getPageSize());
		return p;
	}

	@Override
	public AppVersion queryOne(Long id) {
		
		return appVersionMng.find(id);
	}

	@Override
	public void delAppVersion(Long id) {
		AppVersion appVersion=	appVersionMng.find(id);
		appVersion.setStatus(0);
		appVersionMng.update(appVersion);
		
	}

	@Override
	public void addAppVersion(AppVersion appVersion) {
		appVersion.setStatus(1);
		appVersion.setCreateTime(new Timestamp(System.currentTimeMillis()));
		appVersionMng.save(appVersion);
		
	}

	@Override
	public void updateAppVersion(AppVersionVo appVersionVo) {
		// TODO Auto-generated method stub
		
	}

}
