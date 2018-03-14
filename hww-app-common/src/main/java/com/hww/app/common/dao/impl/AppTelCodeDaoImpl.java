package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppTelCodeDao;
import com.hww.app.common.entity.AppNationalTelCode;
import com.hww.base.common.util.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AppTelCodeDao")
public class AppTelCodeDaoImpl extends LocalEntityDaoImpl<Long, AppNationalTelCode> implements AppTelCodeDao {

    @Override
    public List<AppNationalTelCode> codeList() {
        Finder f = Finder.create("from AppNationalTelCode bean");
        List<AppNationalTelCode> telCodes = (List<AppNationalTelCode>) find(f);
        if (telCodes != null && telCodes.size() > 0) {
            return telCodes;
        }
        return null;
    }
}
