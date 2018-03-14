package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppMemberNearbyQueryDao;
import com.hww.app.common.dao.AppRecommConfigDao;
import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.manager.AppRecommConfigMng;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("appRecommConfigMng")
@Transactional
public class AppRecommConfigMngImpl extends BaseEntityMngImpl<Long, AppRecommConfig, AppRecommConfigDao> implements AppRecommConfigMng {

    @Autowired
    private AppRecommConfigDao appRecommDao;
    @Autowired
    public void setQueryDao(AppRecommConfigDao appRecommDao) {
        super.setEntityDao(appRecommDao);
        this.appRecommDao = appRecommDao;
    }

    @Override
    public void saveDefRecomm(List<AppRecommConfig> list) {
    	appRecommDao.saveDefRecomm(list);
    }

    @Override
    public Pagination queryRecommList(Integer type, AppRecommConfigVo vo) {
        return appRecommDao.queryRecommList(type, vo);
    }

    @Override
    public void updateRecommNum(List<AppRecommConfig> appRecommConfigList) {
    	appRecommDao.updateRecommNum(appRecommConfigList);
    }

    public AppRecommConfig queryRecommById(Long columnId) {
    	AppRecommConfig cmsRecommConfig = appRecommDao.find(columnId);
        return cmsRecommConfig;
    }

    @Override
    public void saveConfig(AppRecommConfig config) {
    	appRecommDao.save(config);
    }

    @Override
    public List<AppRecommConfig> selectRecommConfigList() {
        return appRecommDao.selectRecommConfigList();
    }

	@Override
	public List<AppRecommConfigDto> loadAllRecomm() {
		Finder finder=Finder.create("from AppRecommConfig");
		finder.append("where status=1 ");
		return (List<AppRecommConfigDto>) appRecommDao.find(finder);
	}

	

	@Override
	public Pagination queryRecommLists(AppRecommConfigVo vo) {
		// TODO Auto-generated method stub
		return appRecommDao.queryRecommLists(vo);
	}

	@Override
	public AppRecommConfig queryAppRecommConfig(Long columnId) {
		// TODO Auto-generated method stub
		return appRecommDao.find(columnId);
	}

	@Override
	public List<AppRecommConfigDto> loadRecommByType(Integer type) {
		Finder finder=Finder.create("from AppRecommConfig");
		finder.append("where status=:status").setParam("status", Short.valueOf("1"));
		finder.append(" and type=:type").setParam("type", type);
		return (List<AppRecommConfigDto>) find(finder);
	}

	@Override
	public AppRecommConfig queryListById(Long id) {
		
		return appRecommDao.find(id);
	}

	@Override
	public void updateAppRecomm(AppRecommConfig appRecommConfig) {
		// TODO Auto-generated method stub
		appRecommDao.update(appRecommConfig);
	}

	@Override
	public void delRecommById(Long columnId) {	
		appRecommDao.delete(columnId);
	}

	
}
