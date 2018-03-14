package com.hww.app.common.manager;

import java.util.List;

import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.base.common.page.Pagination;

public interface AppTelCodeMng {
		public void addTelCode(AppNationalTelCode telCode);
		public void delTelCode(AppNationalTelCode telCode);
		public AppNationalTelCode queryOne(Long telId);
		public void updateTelCode(AppNationalTelCode telCode);
		public Pagination telCodeList(Integer pageNo,Integer pageSize);
}
