package com.hww.app.webservice.service.impl;

import com.hww.app.common.dto.AppRecommConfigDto;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppRecommConfig;
import com.hww.app.common.manager.AppCategoryMng;
import com.hww.app.common.manager.AppRecommConfigMng;
import com.hww.app.common.vo.AppRecommConfigVo;
import com.hww.app.webservice.service.AppCategoryService;
import com.hww.app.webservice.service.AppRecommConfigService;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.StringUtils;
import com.hww.base.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("appRecommConfigService")
@Transactional
public class AppRecommConfigServiceImpl implements AppRecommConfigService {

    @Autowired
    private AppCategoryService appColumnService;
    @Autowired
    private AppRecommConfigMng appRecommMng;
    @Autowired
    private AppCategoryMng appCategoryMng;


    @Override
    public void saveRecomm(Integer siteId, List<AppCategory> appCategoryList) {
        if (null == appCategoryList || appCategoryList.size() == 0) {
            return;
        }
        List<AppRecommConfig> list = new ArrayList<>();
        for (AppCategory cmsColumn : appCategoryList) {
        	AppRecommConfig config = new AppRecommConfig();
            config.setColumnId(cmsColumn.getColumnId());
            config.setType(1);
            config.setSiteId(siteId);
            config.setRecommNum(0);
            config.setStatus((short)1);
            config.setColumnName(cmsColumn.getColumnName());
            config.setCreateTime(TimeUtils.getDateToTimestamp());
            config.setLastModifyTime(TimeUtils.getDateToTimestamp());
            list.add(config);
        }
        appRecommMng.saveDefRecomm(list);
    }

    @Override
    public Pagination queryRecommList(Integer type, AppRecommConfigVo vo) {
        return appRecommMng.queryRecommList(type, vo);
    }

    @Override
    public void updateRecommNum(String columnIds) {
        if (StringUtils.isBlank(columnIds)) {
            return;
        }
        String[] ids = columnIds.split("#");
        List<AppRecommConfig> appRecommConfigList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            String[] strArray = ids[i].split(",");
            Long columnId = Long.parseLong(strArray[0]);
            Long recommNum = Long.parseLong(strArray[1]);
            AppRecommConfig appRecommConfig = appRecommMng.queryRecommById(columnId);
            if(appRecommConfig.getRecommNum() != recommNum.intValue()) {
            	appRecommConfig.setRecommNum(recommNum.intValue());
            	appRecommConfig.setLastModifyTime(TimeUtils.getDateToTimestamp());
            	appRecommConfigList.add(appRecommConfig);
            }
        }
        appRecommMng.updateRecommNum(appRecommConfigList);
    }

    @Override
    public void saveNewThing(Integer siteId) {
    	AppRecommConfig config = new AppRecommConfig();
        config.setColumnId(0L);
        config.setType(3);
        config.setSiteId(siteId);
        config.setRecommNum(0);
        config.setStatus((short)1);
        config.setColumnName("新鲜事");
        config.setCreateTime(TimeUtils.getDateToTimestamp());
        config.setLastModifyTime(TimeUtils.getDateToTimestamp());
        appRecommMng.saveConfig(config);
    }

    @Override
    public void saveSpecialList(Integer siteId) {
    	AppRecommConfig config = new AppRecommConfig();
        config.setColumnId(-1L);
        config.setType(2);
        config.setSiteId(siteId);
        config.setRecommNum(0);
        config.setStatus((short)1);
        config.setColumnName("专题");
        config.setCreateTime(TimeUtils.getDateToTimestamp());
        config.setLastModifyTime(TimeUtils.getDateToTimestamp());
        appRecommMng.saveConfig(config);
    }

	
    @Scheduled(fixedDelay = 1000*60*3)
	@CacheEvict(value = "loadAllRecomm",allEntries=true)
	public void loadAllRecomm_delete_from_cache() {
	}
    
  //  @Cacheable(value = "loadAllRecomm")
	public List<AppRecommConfigDto> loadAllRecomm() {
		
		return appRecommMng.loadAllRecomm();
	}

	public List<AppRecommConfigDto> loadRecommByType(Integer type) {
		
		return appRecommMng.loadRecommByType(type);
	}
	
	
	
	
	public AppRecommConfig saveAppCategory(AppCategory appCategory) {
		 appCategoryMng.save(appCategory);
		 Long appCategoryId=appCategory.getId();
		 AppRecommConfig appRecomm=new AppRecommConfig();
		 appRecomm.setColumnId(appCategoryId);
		 appRecomm.setColumnName(appCategory.getColumnName());
		 appRecomm.setStatus((short)1);
		 appRecomm.setRecommNum(0);
		 appRecomm.setCreateTime(TimeUtils.getDateToTimestamp());
		 appRecomm.setType(appCategory.getSpecialType());
		 appRecommMng.saveConfig(appRecomm);
		 AppRecommConfig appRecommConfig=appRecommMng.queryAppRecommConfig(appCategoryId);
		return appRecommConfig;
		
	}
	public Pagination queryRecommLists(AppRecommConfigVo vo) {
		return appRecommMng.queryRecommLists(vo);
	}

	
	public AppRecommConfigVo queryAppRecommConfig(Long columnId) {
		AppRecommConfig appRecommConfig=	appRecommMng.queryAppRecommConfig(columnId);
		AppRecommConfigVo appRecommConfigVo=BeanMapper.map(appRecommConfig, AppRecommConfigVo.class);
		return appRecommConfigVo;
	}

	
	
	
	@Override
	public AppRecommConfig queryListById(Long id) {
		
		return appRecommMng.queryListById(id);
	}

	@Override
	public void updateAppRecomm(AppRecommConfigVo appRecommConfigVo) {
		AppRecommConfig appRecommConfig=BeanMapper.map(appRecommConfigVo, AppRecommConfig.class);
		appRecommMng.updateAppRecomm(appRecommConfig);
		
	}

	@Override
	public void delRecommById(Long columnId) {
		appRecommMng.delRecommById(columnId);
		
	}

	@Override
	public void saveAppRecommConfig(AppRecommConfig appRecommConfig) {
		// TODO Auto-generated method stub
		appRecommMng.saveConfig(appRecommConfig);
	}
	

}
