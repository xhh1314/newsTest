package com.hww.app.webservice.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hww.app.common.entity.AppCategory;
import com.hww.app.common.entity.AppUserRCategory;
import com.hww.app.common.manager.AppCategoryMng;
import com.hww.app.common.manager.AppUserRCategoryMng;
import com.hww.app.common.vo.AppCategoryVo;
import com.hww.app.common.vo.AppHomeCategoryVo;
import com.hww.app.common.vo.ZAppCategoryVo;
import com.hww.app.common.vo.ZAppHomeCategoryVo;
import com.hww.app.webservice.config.CacheClearFixedDelayConfig;
import com.hww.app.webservice.service.AppCategoryService;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.CopyBean;
import com.hww.base.util.DateUtils;
import com.hww.base.util.R;
import com.hww.base.util.TimeUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service("appCategoryService")
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {

    @Autowired
    private AppCategoryMng appCategoryMng;

    @Autowired
    private AppUserRCategoryMng appUserRCategoryMng;
    
       @Scheduled(fixedDelay = 1000*60*3)
	   @CacheEvict(value = "loadAllEnableColumns",allEntries=true)
	   public void loadAllEnableColumns_delete_from_cache() {
	   }
    
    @Cacheable(value="loadAllEnableColumns",key="'loadAllEnableColumns_'")
    @Override
    public  List<AppCategory> loadAllEnableColumns(){
    	
    	List<AppCategory> cateList=appCategoryMng.loadAllColumns();
    	
    	if(cateList!=null&&!cateList.isEmpty()) {
    		//首先过滤掉不显示频道--@zht 2018/02/10 from ligang expect
     		cateList=cateList.stream().filter(val->val.getIsDisplay()!=null&&val.getIsDisplay().intValue()==1).collect(Collectors.toList());
    		
    		for(AppCategory cate : cateList ) {
    			//被禁用或者删除
    			if(cate.getStatus()!=null&&cate.getStatus().intValue()!=1) {
    				appUserRCategoryMng.deletUserRCategoryByColumnId(cate.getColumnId());
    			}
    		}
    	}
    	return cateList;
    }
    
  
    
    private List<AppCategory> loadAndSaveDedaultAndRequiredColumn(List<AppCategory> allColumns,Long userId, String imei,boolean isByUserId){
    	
    	List<AppCategory> readerPaperColumn =Lists.newArrayList();
    	
    		AppCategory column_tuijian=allColumns.stream().filter(val->val.getColumnId().equals(new Long(1L))).collect(Collectors.toList()).get(0);
    		AppCategory column_dubao=allColumns.stream().filter(val->val.getColumnId().equals(new Long(2L))).collect(Collectors.toList()).get(0);
    		List<AppCategory> defaultColumnList = allColumns.stream().filter(val->Short.valueOf("1").equals(val.getIsDefault())).collect(Collectors.toList());
    		readerPaperColumn.add(column_tuijian);
    		readerPaperColumn.add(column_dubao);
    		readerPaperColumn.addAll(defaultColumnList);
    	for(AppCategory column:readerPaperColumn ) {
    		AppUserRCategory  userRCategory=new AppUserRCategory();
    		userRCategory.setColumnId(column.getColumnId());
    		userRCategory.setCreateTime(TimeUtils.getDateToTimestamp());
    		userRCategory.setLastModifyTime(TimeUtils.getDateToTimestamp());
    		userRCategory.setStatus(Short.valueOf("1"));
    		userRCategory.setSiteId(1);
    		userRCategory.setSort(column.getSort());
    		//userid 或者imei只准出现一个，另一个一定为null---防止一个imei出现在两个用户那里
    		if(isByUserId) {
    			userRCategory.setUserId(userId);
        		userRCategory.setImei(null);
    		}else {
    			userRCategory.setUserId(null);
        		userRCategory.setImei(imei);
    		}
    		appUserRCategoryMng.save(userRCategory);
    	}
    	return readerPaperColumn;
    }
    
    	@Scheduled(fixedDelay = 1000*60*3)
	   @CacheEvict(value = "loadUserAppCate",allEntries=true)
	   public void loadUserAppCate_delete_from_cache() {
	   }
    
    @Cacheable(value="loadUserAppCate",key="'loadUserAppCate_'+#userId+'_'+#imei")
    @Override
    public ZAppHomeCategoryVo loadUserAppCate(Long userId, String imei) {
    	ZAppHomeCategoryVo homeCategoryVo=new ZAppHomeCategoryVo();
    	if(userId==null&&!StringUtils.hasText(imei)) {
    		return homeCategoryVo;
    	}
    	//直接加载所有可用频道
    	List<AppCategory> allColumns=loadAllEnableColumns();
    	allColumns=allColumns.stream().sorted((v1,v2)->{return v1.getSort()-v2.getSort();}).collect(Collectors.toList());
    	//by userId or imei
    	boolean isByUserId=(userId!=null)?true:false;
    	
    	List<AppCategory> indexColumns =  Lists.newArrayList();
    	//加载用户订阅的频道
    	List<AppUserRCategory> list=appUserRCategoryMng.loadMemberRCategoryList(userId, imei);
    	
    	if(list==null||list.isEmpty()) {//用户第一次请求，此处数据为空               
    		//userid 或者imei只准出现一个，另一个一定为null---防止一个imei出现在两个用户那里
    		indexColumns = loadAndSaveDedaultAndRequiredColumn(allColumns,userId, imei,isByUserId);
    		List<ZAppCategoryVo> indexColumnVOList=toVoList(indexColumns,"");
    		indexColumnVOList =indexColumnVOList.stream().sorted((v1,v2)-> {return v1.getSort()-v2.getSort();}).collect(Collectors.toList());
    		homeCategoryVo.setIndexColumns(indexColumnVOList);
    		
    	}else {//用户已经请求过了
    		indexColumns = appCategoryMng.loadAppCategoryByIds(list.stream().map(val->val.getColumnId()).collect(Collectors.toList()));
    		
    		
    		List<ZAppCategoryVo> indexColumnVOList=toVoList(indexColumns,"");
    		
    		//按照用户自定义排序，重新设置sort
        	for(ZAppCategoryVo vo : indexColumnVOList) {
        		for(AppUserRCategory ur:list) {
        			if(vo.getColumnId().equals(ur.getColumnId())) {
        				vo.setSort(ur.getSort());
        			}
        		}
        	}
        	
        	//首页重新排序
        	indexColumnVOList =indexColumnVOList.stream().sorted((v1,v2)-> {return v1.getSort()-v2.getSort();}).collect(Collectors.toList());
        	
        	
        	for(AppCategory allx:allColumns ) {
        		//找到用户未订阅的，而且是必须订阅的
        		if(!indexColumns.contains(allx)&&allx.getIsDefault()!=null&&allx.getIsDefault().intValue()==1) {
        			ZAppCategoryVo vo=new ZAppCategoryVo();
        			try {
        				BeanUtils.copyProperties(vo, allx);
        			} catch (Exception e) {
        				e.printStackTrace();
        				continue;
        			}
        			indexColumnVOList.add(vo);
        		}
        	}
        	
    		homeCategoryVo.setIndexColumns(indexColumnVOList);
    		
    		
    		
    	}
    	
    	
    	//给后面lamad表达式用
    	final List<AppCategory> finalIndexColumns=indexColumns;
    	//首页
    	
    	//====================首页结束==========开始加载未订阅的频道======================================================
    	List<HashMap<String, Object>> unSubscribeColumns=Lists.newArrayList();
    	
    	List<AppCategory> firstLevelColumns=allColumns.stream()
    			.filter(val->Long.valueOf("19").equals(val.getParentId()))//19是根节点
    			.filter(val->!Long.valueOf("21").equals(val.getParentId()))//21 是包含固定频道--推荐和读报
    			.collect(Collectors.toList());
    	if(firstLevelColumns==null||firstLevelColumns.isEmpty()) {
    		homeCategoryVo.setUnSubscribeColumns(unSubscribeColumns);
    		return homeCategoryVo;
    	}
    	
    	for(AppCategory parentColumn : firstLevelColumns ) {
    		List<AppCategory> childList=allColumns.stream()
    				.filter(val->parentColumn.getColumnId().equals(val.getParentId()))//寻找子类
    				.filter(val->!finalIndexColumns.contains(val))//已经订阅的频道--过滤掉
    				.collect(Collectors.toList());
    		
    		HashMap<String, Object> oneData=Maps.newHashMap();
    		oneData.put("name", parentColumn.getColumnName());
    		oneData.put("id", parentColumn.getColumnId());
    		
    		List<ZAppCategoryVo> childVoList=toVoList(childList,parentColumn.getColumnName());
    		oneData.put("child", childVoList);
    		unSubscribeColumns.add(oneData);
    	}
    	homeCategoryVo.setUnSubscribeColumns(unSubscribeColumns);
    	return homeCategoryVo;
    }
    
    
    private List<ZAppCategoryVo> toVoList(List<AppCategory> indexColumns,String parentName){
    	List<ZAppCategoryVo> finalIndexColumnList=Lists.newArrayList();
    	if(indexColumns==null||indexColumns.isEmpty()) {
    		return finalIndexColumnList;
    	}
    	for(AppCategory appCategory:indexColumns) {
			ZAppCategoryVo vo=new ZAppCategoryVo();
			try {
				BeanUtils.copyProperties(vo, appCategory);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vo.setParentName(parentName);
			finalIndexColumnList.add(vo);
		}
    	finalIndexColumnList =finalIndexColumnList.stream().sorted((v1,v2)-> {return v2.getSort()-v1.getSort();}).collect(Collectors.toList());
    	return finalIndexColumnList;
    }
    
    
    @CacheEvict(value="loadUserAppCate",key="'loadUserAppCate_'+#userId+'_'+#imei")
    @Override
    public void userCategorySorting2(String columnSorting, Long userId, String imei) {
    	if(userId==null&&!StringUtils.hasText(imei)&&!StringUtils.hasText(columnSorting)) {
    		return ;
    	}
    	if(!StringUtils.hasText(columnSorting)) {
    		return ;
    	}
    	boolean isByUserId=(userId!=null)?true:false;
    	
    	String[] columns= columnSorting.split(",");
    	
    	if(columns==null||columns.length==0) {
    		return;
    	}
    	List<Long> columnsIndList=Stream.of(columns).map(val->Long.valueOf(val)).collect(Collectors.toList());
    	columnsIndList.remove(1L);
    	columnsIndList.add(0,1L);
    	
    	appUserRCategoryMng.deletUserRCategory(userId, imei);
    	int index=1;
    	for(Long cid:columnsIndList ) {
    		if(cid!=null) {
    			Long columnId=Long.valueOf(cid);
    			AppUserRCategory  userRCategory=new AppUserRCategory();
        		userRCategory.setColumnId(columnId);
        		userRCategory.setCreateTime(TimeUtils.getDateToTimestamp());
        		userRCategory.setLastModifyTime(TimeUtils.getDateToTimestamp());
        		userRCategory.setStatus(Short.valueOf("1"));
        		userRCategory.setSiteId(1);
        		userRCategory.setSort(index);
        			index++;
        		//userid 或者imei只准出现一个，另一个一定为null---防止一个imei出现在两个用户那里
        		if(isByUserId) {
        			userRCategory.setUserId(userId);
            		userRCategory.setImei(null);
        		}else {
        			userRCategory.setUserId(null);
            		userRCategory.setImei(imei);
        		}
        		appUserRCategoryMng.save(userRCategory);
    		}
    	}
    }
    
    //=============================================================================================================================
    /* 
     * <p>Title: queryCategoryList</p> 
     * <p>Description:更新叶子节点获取逻辑，去除用户已订阅的频道 </p> 
     */
    @Override
    public AppHomeCategoryVo queryCategoryList(Long userId, String imei) {
    	//查询用户订阅的频道信息，确认用户是否为首次登陆
    	List<HashMap<String, Object>> confirmUserSubscribedColumnAll = appCategoryMng.userSubscribedColumnAll(userId, imei);
    	//用户订阅频道顺序,首页展示index
        List<AppCategoryVo> indexColumnsVo = new ArrayList<AppCategoryVo>();
    	//向用户表中添加用户订阅
    	if(confirmUserSubscribedColumnAll==null|confirmUserSubscribedColumnAll.size()==0) {
    		if(imei!=null|userId!=null) {
                //添加推荐栏目
                List<AppCategory> recommendColumn = appCategoryMng.selectRecommendColumn();
                if(recommendColumn!=null&&recommendColumn.size()==1) {
                	AppCategory appCategory = recommendColumn.get(0);
                	AppUserRCategory appUserRCategory = new AppUserRCategory();
                	appUserRCategory.setColumnId(appCategory.getColumnId());
                	appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                	if(imei!=null) {
                		appUserRCategory.setImei(imei);
                	}
                	if(userId!=null) {
                		appUserRCategory.setUserId(userId);
                	}
                	appUserRCategory.setSiteId(1);
                	appUserRCategory.setSort(0);
                	appUserRCategory.setStatus(new Short("1"));
                	appUserRCategoryMng.save(appUserRCategory);
                }
                //添加读报栏目
                List<AppCategory> readerPaperColumn = appCategoryMng.selectReaderPaperColumn();
                if(readerPaperColumn!=null&&readerPaperColumn.size()==1) {
                	AppCategory appCategory = readerPaperColumn.get(0);
                	AppUserRCategory appUserRCategory = new AppUserRCategory();
                	appUserRCategory.setColumnId(appCategory.getColumnId());
                	appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                	if(imei!=null) {
                		appUserRCategory.setImei(imei);
                	}
                	if(userId!=null) {
                		appUserRCategory.setUserId(userId);
                	}
                	appUserRCategory.setSiteId(1);
                	appUserRCategory.setSort(1);
                	appUserRCategory.setStatus(new Short("1"));
                	appUserRCategoryMng.save(appUserRCategory);
                }
                //默认栏目
                List<AppCategory> defaultColumn = appCategoryMng.selectDefaultColumn(userId, imei);
                if(defaultColumn!=null) {
                	for(int i=0;i<defaultColumn.size();i++) {
                		AppCategory appCategory = defaultColumn.get(i);
                    	AppUserRCategory appUserRCategory = new AppUserRCategory();
                    	appUserRCategory.setColumnId(appCategory.getColumnId());
                    	appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    	if(imei!=null) {
                    		appUserRCategory.setImei(imei);
                    	}
                    	if(userId!=null) {
                    		appUserRCategory.setUserId(userId);
                    	}
                    	appUserRCategory.setSiteId(1);
                    	appUserRCategory.setSort(i+2);
                    	appUserRCategory.setStatus(new Short("1"));
                    	appUserRCategoryMng.save(appUserRCategory);
                	}
                }
                //用户首次登陆，加载默认订阅列表
                List<HashMap<String, Object>> userSubscribedColumnAll = appCategoryMng.userSubscribedColumnAll(userId, imei);
                List<AppCategoryVo> AppCategoryVo = this.transHashMapToEntity(userSubscribedColumnAll);
                for(AppCategoryVo a: AppCategoryVo) {
                	indexColumnsVo.add(a);
                }
        	}
    		//如果用户未传递任何身份标识信息
    		else if(imei==null&userId==null) {
    			 //添加推荐栏目
                List<AppCategory> recommendColumn = appCategoryMng.selectRecommendColumn();
                if(recommendColumn!=null&&recommendColumn.size()==1) {
                	AppCategoryVo appCategoryVo = new AppCategoryVo();
                	CopyBean.copyNotNull(recommendColumn.get(0), appCategoryVo);
                	indexColumnsVo.add(appCategoryVo);
                }
                //添加读报栏目
                List<AppCategory> readerPaperColumn = appCategoryMng.selectReaderPaperColumn();
                if(readerPaperColumn!=null&&readerPaperColumn.size()==1) {
                	AppCategoryVo appCategoryVo = new AppCategoryVo();
                	CopyBean.copyNotNull(readerPaperColumn.get(0), appCategoryVo);
                	indexColumnsVo.add(appCategoryVo);
                }
                //默认栏目
                List<AppCategory> defaultColumn = appCategoryMng.selectDefaultColumn(userId, imei);
                if(defaultColumn!=null) {
                	for(int i=0;i<defaultColumn.size();i++) {
                    	AppCategoryVo appCategoryVo = new AppCategoryVo();
                    	CopyBean.copyNotNull(defaultColumn.get(i), appCategoryVo);
                    	indexColumnsVo.add(appCategoryVo);
                	}
                }
    		}
    	}
    	else {
    		//存在用户登录数据，直接加载用户登录数据
    		List<AppCategoryVo> AppCategoryVo = this.transHashMapToEntity(confirmUserSubscribedColumnAll);
            for(AppCategoryVo a: AppCategoryVo) {
            	indexColumnsVo.add(a);
            }
    	}
    	//用户未订阅频道数据封装
        List<HashMap<String, Object>> userSubscribedHash = appCategoryMng.userSubscribedColumn(userId, imei);
        List<AppCategoryVo> userSubscribedColumn = new ArrayList<AppCategoryVo>();
        if(userSubscribedHash!=null|userSubscribedHash.size()!=0) {
        	 userSubscribedColumn = this.transHashMapToEntity(userSubscribedHash);
        }
        //用户未订阅的频道
        List<HashMap<String, Object>> unSublist = new ArrayList<HashMap<String, Object>>();
    	//查询根节点信息
    	AppCategory rootNode = appCategoryMng.selectRootNode();
    	if(rootNode!=null) {
    		List<HashMap<String, Object>> midelNodeColumn = appCategoryMng.selectLeafNodeColumnByParentId(rootNode.getColumnId());
    		
    		//遍历中间节点，查询叶子节点
            for (HashMap<String, Object> a : midelNodeColumn) {
                //中间节点id
                long midleNodeId = Long.parseLong(a.get("column_id").toString());
                String columnName = a.get("column_name").toString();
                //去除固定频道中读报和推荐两个栏目,在我的频道中显示
                if(midleNodeId!=21) {
                    HashMap<String, Object> midelNode = new HashMap<String, Object>();
                    midelNode.put("name", columnName);
                    //叶子节点包含默认频道
                    List<HashMap<String, Object>> leafNodeColumnByParentId = new ArrayList<HashMap<String, Object>>();
                    if(imei!=null|userId!=null) {
                        leafNodeColumnByParentId = appCategoryMng.selectLeafNodeColumnByParentId(midleNodeId);
                        //从叶子节点中剔除用户已订阅的频道
                        for(int i=0;i<leafNodeColumnByParentId.size();i++) {
                        	//叶子节点id
                        	long leafId = Long.parseLong(leafNodeColumnByParentId.get(i).get("column_id").toString());
                        	//用户订阅的频道id
                        	for(AppCategoryVo ua : userSubscribedColumn) {
                        		//如果二者相等，去除该节点
                        		if(leafId==ua.getColumnId()) {
                        			leafNodeColumnByParentId.remove(i);
                        			i--;
                        		}
                        	}
                        }
                    }
                    else if(imei==null&userId==null) {
                    	//不存在叶子节点，加载叶子节点的同时排除默认频道和固定频道
                    	leafNodeColumnByParentId = appCategoryMng.selectLeafNodeColumnByParentIdNotContain(midleNodeId);
                    }

                    List<AppCategoryVo> leafNodeColumnByParent = this.transHashMapToEntity(leafNodeColumnByParentId);
                    midelNode.put("id", Long.parseLong(a.get("column_id").toString()));
                    midelNode.put("child", leafNodeColumnByParent);
                    unSublist.add(midelNode);
                }
            }
    	}
        AppHomeCategoryVo appCategoryList = new AppHomeCategoryVo();
        appCategoryList.setUnSubscribeColumns(unSublist);
        appCategoryList.setIndexColumns(indexColumnsVo);
        return appCategoryList;

    }

    @Override
    public R saveUserCategory(Long columnId, Long userId, String imei) {

        //确认用户是否订阅了该频道
        AppUserRCategory userCategory = appUserRCategoryMng.confirmUserCategory(columnId, userId, imei);
        if (userCategory.getColumnId() != null) {
            return R.error(1, "该频道已订阅");
        }

        try {
            //获取频道信息
            AppCategory appCategory = appCategoryMng.find(columnId);
            AppUserRCategory appUserRCategory = new AppUserRCategory();
            appUserRCategory.setColumnId(appCategory.getColumnId());
            appUserRCategory.setStatus(appCategory.getStatus());
            appUserRCategory.setSiteId(appCategory.getSiteId());
            //如果存在userId,则优先使用UserId,否则使用IMEI
            if (userId != null) {
                appUserRCategory.setUserId(userId);
                appUserRCategory.setImei(null);
            } else {
                appUserRCategory.setImei(imei);
            }
            //将该频道排在用户订阅的最后一位
            AppUserRCategory maxsort = this.maxUserCategory(appUserRCategory);
            if (maxsort != null) {
                appUserRCategory.setSort(maxsort.getSort() + 1);
            } else {
                //读报和推荐频道排前两位
                appUserRCategory.setSort(2);
            }
            //确认用户频道关系表中是否存在默认频道和推荐频道
            List<HashMap<String, Object>> confirmCategoryOfReadPaper = appCategoryMng.confirmCategoryOfReadPaper(userId, imei);
            //如果用户频道关系表中存在这两张表，直接订阅频道
            if (confirmCategoryOfReadPaper.size() == 2) {
                //更新频道用户关系表
                appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                appUserRCategoryMng.save(appUserRCategory);
            }
            //如果只存在一张表,则添加另外一张表
            else if (confirmCategoryOfReadPaper.size() == 1) {
                //判断已经存在的频道信息
                for (HashMap<String, Object> h : confirmCategoryOfReadPaper) {
                    long getId = Long.parseLong(h.get("column_id").toString());
                    List<AppCategory> readerPaperColumn = appCategoryMng.selectReaderPaperColumn();
                    List<AppCategory> recommendColumn = appCategoryMng.selectRecommendColumn();
                    //如果推荐频道已经存在,则添加读报频道进用户和订阅频道关系表
                    if (getId == readerPaperColumn.get(0).getColumnId()) {
                        AppUserRCategory saveAppUserRCategory = new AppUserRCategory();
                        saveAppUserRCategory.setColumnId(recommendColumn.get(0).getColumnId());
                        saveAppUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        if(imei!=null) {
                        	saveAppUserRCategory.setImei(imei);
                        }
                        saveAppUserRCategory.setSiteId(recommendColumn.get(0).getSiteId());
                        saveAppUserRCategory.setSort(1);
                        saveAppUserRCategory.setStatus(new Short("1"));
                        if(userId!=null) {
                        	saveAppUserRCategory.setUserId(userId);
                        }
                    	appUserRCategoryMng.save(saveAppUserRCategory);
                        //添加订阅的频道
                        appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        appUserRCategoryMng.save(appUserRCategory);
                    }
                    //否则添加另外一条
                    else {
                        AppUserRCategory saveAppUserRCategory = new AppUserRCategory();
                        saveAppUserRCategory.setColumnId(recommendColumn.get(0).getColumnId());
                        saveAppUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        if(imei!=null) {
                        	saveAppUserRCategory.setImei(imei);
                        }
                        saveAppUserRCategory.setSiteId(recommendColumn.get(0).getSiteId());
                        saveAppUserRCategory.setSort(1);
                        saveAppUserRCategory.setStatus(new Short("1"));
                        if(userId!=null) {
                        	saveAppUserRCategory.setUserId(userId);
                        }
                        appUserRCategoryMng.save(saveAppUserRCategory);
                        //添加订阅的频道
                        appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        appUserRCategoryMng.save(appUserRCategory);
                    }
                }
            }
            //如果一个频道都未订阅则订阅这两个频道
            else {
                //添加两个首页频道
                List<AppCategory> readerPaperColumn = appCategoryMng.selectReaderPaperColumn();
                List<AppCategory> recommendColumn = appCategoryMng.selectRecommendColumn();
                AppUserRCategory saveReaderPaperColumn = new AppUserRCategory();
                saveReaderPaperColumn.setColumnId(readerPaperColumn.get(0).getColumnId());
                saveReaderPaperColumn.setCreateTime(new Timestamp(System.currentTimeMillis()));
                if(imei!=null) {
                	saveReaderPaperColumn.setImei(imei);
                }
                saveReaderPaperColumn.setSiteId(recommendColumn.get(0).getSiteId());
                saveReaderPaperColumn.setSort(0);
                saveReaderPaperColumn.setStatus(new Short("1"));
                if(userId!=null) {
                	saveReaderPaperColumn.setUserId(userId);
                }
                appUserRCategoryMng.save(saveReaderPaperColumn);

                AppUserRCategory saveRecommendColumn = new AppUserRCategory();
                saveRecommendColumn.setColumnId(recommendColumn.get(0).getColumnId());
                saveRecommendColumn.setCreateTime(new Timestamp(System.currentTimeMillis()));
                if(imei!=null) {
                	saveRecommendColumn.setImei(imei);
                }
                saveRecommendColumn.setSiteId(recommendColumn.get(0).getSiteId());
                saveRecommendColumn.setSort(1);
                saveRecommendColumn.setStatus(new Short("1"));
                if(userId!=null) {
                	saveRecommendColumn.setUserId(userId);
                }
                appUserRCategoryMng.save(saveRecommendColumn);
                //添加订阅的频道
                appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                appUserRCategoryMng.save(appUserRCategory);
            }
            
            return R.ok().put("data", "订阅成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(1, "保存失败");
        }

    }

    //用户接口：查询订阅频道的数量
    private AppUserRCategory maxUserCategory(AppUserRCategory appUserRCategory) {

        return appUserRCategoryMng.maxUserCategory(appUserRCategory);
    }

    /* 
     * <p>Title: deleteUserCategory</p> 
     * <p>Description:取消订阅频道 </p>
     */
    @Override
    public void deleteUserCategory(Long columnId, Long userId, String imei) {

        AppCategory appCategory = new AppCategory();
        appCategory.setColumnId(columnId);
        
        //删除用户与频道关系表
        appUserRCategoryMng.deleteUserCategory(appCategory, userId, imei);
        
    }
 
    @Override
    //用户接口
    public AppCategoryVo selectUserCategory(String columnName, Long userId, String imei) {

        List<HashMap<String, Object>> selectUserCategoryByColumnName = appUserRCategoryMng.selectUserCategoryByColumnName(columnName, userId, imei);
        List<AppCategoryVo> appCategoryList = this.transHashMapToEntity(selectUserCategoryByColumnName);

        return appCategoryList.get(0);
    }

    @Override
    //管理员接口
    public void saveCategory(AppCategoryVo appCategoryVo) {

        //方法：按照管理员输入的顺序添加
        AppCategory insertAppCategory = new AppCategory();
        if (appCategoryVo != null) {
            CopyBean.copyNotNull(appCategoryVo, insertAppCategory);
            insertAppCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
            //默认为1,0为读报和推荐两个栏目,不可修改
            insertAppCategory.setSpecialType(1);
            insertAppCategory.setParent(appCategoryMng.find(appCategoryVo.getParentId()));
            appCategoryMng.save(insertAppCategory);
        }

    }

    @Override
    public void userCategorySorting(String columnSorting, Long userId, String imei) {
    	
    	//拿到前台传输的字符串，分割获取用户排序
/*        String str = columnSorting;
        String[] categoryId = str.split(",");
        //删除用户订阅的频道
        appUserRCategoryMng.deleteUserSubColumn(userId, imei);
        List<AppUserRCategory> userSubCategory = appUserRCategoryMng.selectUserSubCategory(userId, imei);
        if(userSubCategory!=null) {
        	for(AppUserRCategory a:userSubCategory) {
        		appUserRCategoryMng.delete(a);
        	}
        }
        
        for(int i=0;i<categoryId.length;i++) {
        	
        	long id = Long.parseLong(categoryId[i]);
        	AppUserRCategory appUserRCategory = new AppUserRCategory();
        	appUserRCategory.setColumnId(id);
        	appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        	if(imei!=null) {
        		appUserRCategory.setImei(imei);
        	}
        	appUserRCategory.setSiteId(1);
        	appUserRCategory.setSort(i);
        	appUserRCategory.setStatus(new Short("1"));
        	appUserRCategory.setUserId(userId);
        	appUserRCategoryMng.save(appUserRCategory);
        }*/

      //拿到前台传输的字符串，分割获取用户排序
        String str = columnSorting;
        String[] categoryId = str.split(",");
        //查询用户订阅的频道列表,判断用户是否对订阅的频道进行了操作
        List<HashMap<String, Object>> userSubscribedColumn = appCategoryMng.userSubscribedColumnAll(userId, imei);
        //判断用户是否修改订阅频道，即先删除再添加另一个频道
        if (userSubscribedColumn.size() == categoryId.length) {
        	//查询当前订阅表单，确定频道信息
            for (int i = 0; i<categoryId.length; i++) {
            	int existCode = 0;
            	//查询已存在表单记录
            	for(int j=0;j<userSubscribedColumn.size();j++) {
            		String alreadySubId = userSubscribedColumn.get(j).get("column_id").toString();
            		if(Long.parseLong(categoryId[i])==Long.parseLong(alreadySubId)) {
                        //同时存在于两张表单中则进行更新操作
                        AppUserRCategory userCategory = appUserRCategoryMng.confirmUserCategory(Long.parseLong(categoryId[i]), userId, imei);
                        if (userCategory != null) {
                            userCategory.setSort(i);
                            if(userId!=null) {
                            	userCategory.setUserId(userId);
                            }
                            if(imei!=null) {
                            	userCategory.setImei(imei);
                            }
                            userCategory.setStatus(new Short("1"));
                            userCategory.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
                            appUserRCategoryMng.update(userCategory);
                            //表示当前记录存在于历史记录表单中
                            existCode = 1;
                        }
            		}
            	}
            	if(existCode!=1) {
            		//当前用户订阅的表单中存在历史订阅表单未订阅的频道,以当前列表中存在的频道id为准
        			AppCategory findAppCat = appCategoryMng.find(Long.parseLong(categoryId[i]));
        			if(findAppCat.getColumnId()!=null) {
        				AppUserRCategory appUserRCategory = new AppUserRCategory();
        				appUserRCategory.setColumnId(findAppCat.getColumnId());
        				appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
        				if(imei!=null) {
        					appUserRCategory.setImei(imei);
        				}
        				appUserRCategory.setSiteId(1);
        				appUserRCategory.setSort(i);
        				appUserRCategory.setStatus(new Short("1"));
        				if(userId!=null) {
        					appUserRCategory.setUserId(userId);
        				}
        				appUserRCategoryMng.save(appUserRCategory);
        				//同时禁用原存在的订阅频道记录
        				long alreadyExistId = Long.parseLong(userSubscribedColumn.get(i).get("column_id").toString());
        				AppUserRCategory userCategory = appUserRCategoryMng.confirmUserCategory(alreadyExistId, userId, imei);
        				if(userCategory.getUserColumnId()!=null) {
            				if(userCategory.getSort()==i) {
            					//禁用该频道
                                if(userId!=null) {
                                	userCategory.setUserId(userId);
                                }
                                if(imei!=null) {
                                	userCategory.setImei(imei);
                                }
            					userCategory.setStatus(new Short("0"));
            					userCategory.setSort(null);
            					appUserRCategoryMng.update(userCategory);
            				}
        				}
        			}
            	}
            }
        }

        else {
        	//如果添加了新订阅的频道
        	if(userSubscribedColumn.size() < categoryId.length) {
        		
                for (int i = 0; i < categoryId.length; i++) {
                    //确认用户是否订阅了该频道
                	Long subId = Long.parseLong(categoryId[i]);
                    AppUserRCategory userCategory = appUserRCategoryMng.confirmUserCategory(subId, userId, imei);
                    //如果订阅,则更新排序
                    if (userCategory.getColumnId() != null) {
                        if(userId!=null) {
                        	userCategory.setUserId(userId);
                        }
                        if(imei!=null) {
                        	userCategory.setImei(imei);
                        }
                        userCategory.setSort(i);
                        userCategory.setStatus(new Short("1"));
                        userCategory.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
                        appUserRCategoryMng.update(userCategory);
                    }
                    //如果未订阅,查询频道添加订阅
                    else {
                    	AppCategory appCategory = appCategoryMng.find(Long.parseLong(categoryId[i]));
                    	if(appCategory.getColumnId()!=null) {
                    		AppUserRCategory appUserRCategory = new AppUserRCategory();
                    		appUserRCategory.setColumnId(appCategory.getColumnId());
                    		appUserRCategory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    		if(imei!=null) {
                    			appUserRCategory.setImei(imei);
                    		}
                    		appUserRCategory.setSiteId(1);
                    		appUserRCategory.setSort(i);
                    		appUserRCategory.setStatus(new Short("1"));
                    		if(userId!=null) {
                    			appUserRCategory.setUserId(userId);
                    		}
                    		appUserRCategoryMng.save(appUserRCategory);
                    	}
                    }
                }
                ArrayList<Long> newSubList = new ArrayList<Long>();
                for(int i=0; i<categoryId.length;i++) {
                	newSubList.add(Long.parseLong(categoryId[i]));
                }
    			//删除用户订阅的列表中不包含当前订阅频道的项
    			for(int i=0;i<userSubscribedColumn.size();i++) {
    				//如果用户订阅的频道中不包含该历史记录
    				if(!newSubList.contains(Long.parseLong(userSubscribedColumn.get(i).get("column_id").toString()))) {
    					long getColumnId = Long.parseLong(userSubscribedColumn.get(i).get("column_id").toString());
						AppUserRCategory confirmUserCategory = appUserRCategoryMng.confirmUserCategory(getColumnId, userId, imei);
                        if(userId!=null) {
                        	confirmUserCategory.setUserId(userId);
                        }
                        if(imei!=null) {
                        	confirmUserCategory.setImei(imei);
                        }
						confirmUserCategory.setStatus(new Short("0"));
						confirmUserCategory.setSort(null);
						appUserRCategoryMng.update(confirmUserCategory);	//delete
    				}
    			}
        		
        	}
        	//如果用户取消订阅了一些频道
        	else if (userSubscribedColumn.size() > categoryId.length) {
        		//存储用户当前订阅频道表单
        		ArrayList<Long> newSubList = new ArrayList<Long>();
    			for(int i=0; i<categoryId.length;i++) {
    				//用户传递的频道id
    				long getColumnId = Long.parseLong(categoryId[i]);
            		for (int j=0; j<userSubscribedColumn.size();j++) {
            			//用户订阅的频道id
            			long columnId = Long.parseLong(userSubscribedColumn.get(j).get("column_id").toString());
            				//二者相等,更新频道顺序
	        				if(getColumnId==columnId) {
	        	                AppUserRCategory userCategory = appUserRCategoryMng.confirmUserCategory(getColumnId, userId, imei);
	        	                if (userCategory.getColumnId() != null) {
	                                if(userId!=null) {
	                                	userCategory.setUserId(userId);
	                                }
	                                if(imei!=null) {
	                                	userCategory.setImei(imei);
	                                }
	        	                    userCategory.setSort(i);
	        	                    userCategory.setStatus(new Short("1"));
	        	                    userCategory.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
	        	                    appUserRCategoryMng.update(userCategory);
	        	                    newSubList.add(getColumnId);
	        	                }
	        				}
            		}

    			}
    			//删除用户订阅的列表中不包含当前订阅频道的项
    			for(int i=0;i<userSubscribedColumn.size();i++) {
    				//如果用户订阅的频道中不包含该历史记录
    				if(!newSubList.contains(Long.parseLong(userSubscribedColumn.get(i).get("column_id").toString()))) {
    					long getColumnId = Long.parseLong(userSubscribedColumn.get(i).get("column_id").toString());
						AppUserRCategory confirmUserCategory = appUserRCategoryMng.confirmUserCategory(getColumnId, userId, imei);
                        if(userId!=null) {
                        	confirmUserCategory.setUserId(userId);
                        }
                        if(imei!=null) {
                        	confirmUserCategory.setImei(imei);
                        }
						confirmUserCategory.setStatus(new Short("0"));
						confirmUserCategory.setSort(null);
						appUserRCategoryMng.update(confirmUserCategory);	//delete
    				}
    			}

        	}
        }
    }

    @Override
    public List<AppCategory> selectAppCategoryInfo(AppCategory appCategory) {

        if (appCategory != null) {
            List<AppCategory> selectAppCategoryInfo = appCategoryMng.selectAppCategoryInfo(appCategory);
            return selectAppCategoryInfo;
        }
        return null;
    }

    @Override
    public void deleteCategory(AppCategory appCategory) {
        if (appCategory != null) {
            appCategoryMng.delete(appCategory);
        }
    }

    @Override
    public R updateCategory(AppCategoryVo appCategoryVo) {
        if (appCategoryVo != null) {
            AppCategory find = appCategoryMng.find(appCategoryVo.getColumnId());
            if (find.getSpecialType() == 0) {
                return R.error(1, "该频道为固定频道不允许编辑");
            }
            AppCategory appCategory = new AppCategory();
            CopyBean.copyNotNull(appCategoryVo, appCategory);
            appCategory.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
            appCategory.setParent(appCategoryMng.find(appCategoryVo.getParentId()));
            appCategoryMng.update(appCategory);
            return R.ok("更新成功");
        }
        return R.ok(" ");
    }

    //将hashmap转换成实体类
    private List<AppCategoryVo> transHashMapToEntity(List<HashMap<String, Object>> list) {
        List<AppCategoryVo> appCategoryList = new ArrayList<AppCategoryVo>();

        for (HashMap<String, Object> h : list) {
            AppCategoryVo appCategory = new AppCategoryVo();

            if (h.get("column_id") != null) {
                appCategory.setColumnId(Long.parseLong(h.get("column_id").toString()));
            }
            if (h.get("column_name") != null) {
                appCategory.setColumnName(h.get("column_name").toString());
            }
            if (h.get("special_type") != null) {
                appCategory.setSpecialType((Integer.parseInt(h.get("special_type").toString())));
            }
            if (h.get("column_type_id") != null) {
                appCategory.setColumnTypeId(Long.parseLong(h.get("column_type_id").toString()));
            }
            if (h.get("column_desc") != null) {
                appCategory.setColumnDesc(h.get("column_desc").toString());
            }
            if (h.get("sort") != null) {
                appCategory.setSort(Integer.parseInt(h.get("sort").toString()));
            }
            if (h.get("logo") != null) {
                appCategory.setLogo(h.get("logo").toString());
            }
            if (h.get("status") != null) {
                appCategory.setStatus(Short.parseShort(h.get("status").toString()));
            }
            if (h.get("site_id") != null) {
                appCategory.setSiteId(Integer.parseInt(h.get("site_id").toString()));
            }
            if (h.get("parent_id") != null) {
                appCategory.setParentId(Long.parseLong(h.get("parent_id").toString()));
            }
            appCategoryList.add(appCategory);
        }
        return appCategoryList;
    }
    private static final Log log = LogFactory.getLog(AppCategoryServiceImpl.class);
    
    
       @Scheduled(fixedDelay = 1000*60*3)
	   @CacheEvict(value = "app_loadCateIdsByColumnId",allEntries=true)
	   public void loadCateIdsByColumnId_delete_from_cache() {
		   log.debug("----loadCateIdsByColumnId_delete_from_cache------------");
	   }
    
    @Cacheable(value = "app_loadCateIdsByColumnId",key="'loadCateIdsByColumnId_'+#columnId")
    @Override
    public List<Long> loadCateIdsByColumnId(Long columnId) {
    	if(columnId==null) {
    		return Lists.newArrayList();
    	}
    	AppCategory appCategory = appCategoryMng.find(columnId);
    	
    	if(appCategory==null||Short.valueOf("0").equals(appCategory.getStatus())) {
    		return Lists.newArrayList(); 
    	}
    	
    	List<Long> cateIds = appCategoryMng.selectCateIdsByColumnId(columnId);
    	
    	return cateIds;
    	 
    	
//        //判断频道是否禁用
//        AppCategory appCategory = appCategoryMng.find(columnId);
//        if (columnId != null) {
//            if (appCategory != null) {
//                if (appCategory.getStatus() == 0) {
//                    List<Long> columnIds = new ArrayList<Long>();
//                    //返回空数组文件
//                    return columnIds;
//                }
//                //如果未禁用,正常返回新闻信息
//                if (appCategory.getStatus() == 1) {
//                    return appCategoryMng.selectCateIdsByColumnId(columnId);
//                }
//            }
//            //如果查询不到频道信息,返回空List
//            List<Long> columnIds = new ArrayList<Long>();
//            return columnIds;
//        }
//        //id为空，返回空list
//        ArrayList<Long> columnIds = new ArrayList<Long>();
//        return columnIds;
    }

	@Override
	public List<AppCategory> loadCustomCategoryList() {
		return appCategoryMng.selectCustomCategoryList();
	}

}
