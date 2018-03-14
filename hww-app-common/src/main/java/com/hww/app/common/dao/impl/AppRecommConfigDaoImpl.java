package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppRecommConfigDao;
import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("appRecommConfigDao")
public class AppRecommConfigDaoImpl extends LocalEntityDaoImpl<Long, AppRecommConfig> implements AppRecommConfigDao {

    @Override
    public void saveDefRecomm(List<AppRecommConfig> list) {
        save(list);
    }

    @Override
    public Pagination queryRecommList(Integer type, AppRecommConfigVo vo) {
        Finder hql = Finder.create("from AppRecommConfig where 1=1 and type=:type ");
        hql.setParam("type", type);
        Pagination p = find(hql, vo.getPageNo(), vo.getPageSize());
        return p;
    }

    @Override
    public void updateRecommNum(List<AppRecommConfig> appRecommConfigList) {
        update(appRecommConfigList);
    }

    @Override
    public List<AppRecommConfig> selectRecommConfigList() {
        Finder hql = Finder.create("from AppRecommConfig");
        List<AppRecommConfig> cmsRecommConfigList = (List<AppRecommConfig>) find(hql);
        return cmsRecommConfigList;
    }


	@Override
	public Pagination queryRecommLists(AppRecommConfigVo vo) {
		 Finder hql = Finder.create("from AppRecommConfig where status=1");
		 Pagination p = find(hql, vo.getPageNo(), vo.getPageSize());
		return p;
	}

	
}
