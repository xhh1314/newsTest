package com.hww.app.common.dao.impl;

import org.springframework.stereotype.Repository;
import com.hww.app.common.dao.AppVersionDao;
import com.hww.app.common.entity.AppVersion;

@Repository("appVersionDao")
public class AppVersionDaoImpl extends LocalEntityDaoImpl<Long, AppVersion> implements AppVersionDao {

}
