package com.hww.app.common.dao;

import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.base.common.dao.IBaseEntityDao;

import java.util.List;

public interface AppTelCodeDao  extends IBaseEntityDao<Long,AppNationalTelCode>{

    List<AppNationalTelCode> codeList();

}
