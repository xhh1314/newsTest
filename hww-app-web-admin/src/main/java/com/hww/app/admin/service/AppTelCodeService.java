package com.hww.app.admin.service;

import java.util.List;

import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.app.common.vo.AppTelCodeVo;
import com.hww.base.common.page.Pagination;

public interface AppTelCodeService {
	public void addTelCode(AppNationalTelCode telCode);
	public void delTelCode(AppTelCodeVo telCode);
	public AppNationalTelCode queryOne(Long telId);
	public void updateTelCode(AppNationalTelCode telCode);
	public Pagination telCodeList(Integer pageNo,Integer pageSize);
}
