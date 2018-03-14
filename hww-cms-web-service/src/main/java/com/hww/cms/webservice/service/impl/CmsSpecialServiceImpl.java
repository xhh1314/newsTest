package com.hww.cms.webservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.cms.common.dto.HCmsQueryDto;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsSpecial;
import com.hww.cms.common.entity.CmsSpecialRCategory;
import com.hww.cms.common.manager.CmsCategoryMng;
import com.hww.cms.common.manager.CmsSpecialMng;
import com.hww.cms.common.manager.CmsSpecialRCategoryMng;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.HCmsSpecialListVo;
import com.hww.cms.common.vo.HCmsSpecialVo;
import com.hww.cms.webservice.config.AppCxtUtil;
import com.hww.cms.webservice.service.CmsContentService;
import com.hww.cms.webservice.service.CmsSpecialService;
import com.hww.file.api.FileFeignClient;
@Service("cmsSpecialService")
@Transactional
public class CmsSpecialServiceImpl implements CmsSpecialService {

	private static final Logger log = LoggerFactory.getLogger(CmsSpecialServiceImpl.class);

	@Autowired
	CmsCategoryMng cmsCategoryMng;
	@Autowired
	CmsSpecialMng cmsSpecialMng;
	@Autowired
	CmsSpecialRCategoryMng cmsSpecialRCategoryMng;
	//@Autowired 
	CmsContentServiceImpl cmsContentService;
	@Autowired
	 FileFeignClient fileInfoFeignClient;
	
	private CmsContentService getCmsContentService() {
		if(cmsContentService==null) {
			cmsContentService =(CmsContentServiceImpl) AppCxtUtil.getCtx().getBean("cmsContentService");
		}
		return cmsContentService;
		
	}
	
	private String getLogUrls(CmsSpecial sp) {
		String url="";
		try {
			if(org.springframework.util.StringUtils.hasText(sp.getLogo())) {
				//处理文件ids 
		    	if(StringUtils.isNotEmpty(sp.getLogo())) {
		    	     String byids = fileInfoFeignClient.getUrlAndDescJsonsByIds(sp.getLogo());
		    	      if(StringUtils.isNotEmpty(byids)) {
		    	    	  //JSONArray sss=JSONArray.parseArray(byids);
		    	    	  List<Map> atlasContent = JSONArray.parseArray(byids, Map.class);// 过时方法
		    	    	  if(atlasContent!=null) {
		    	    		  for(Map map:atlasContent ) {
			    	    		  url=url+"," +map.get("imgUrl");
			    	    	  }
		    	    		  if(org.springframework.util.StringUtils.hasText(url)&&url.length()>1) {
		    	    			  url=url.substring(1);
		    	    		  }
		    	    		 
		    	    	  }
		    	    	  
		    	    	  //cmsContentDataVo.setContent(obj.toString());
		    	      }
		    	}
			}
		}catch (Exception e) {
		}
		return url;
	}
	
	
	@Override
	public HCmsSpecialListVo loadCmsSpecialListVoById(HCmsQueryDto queryDto) {
		CmsSpecial sp=loadSpecialView(queryDto.getSpecialId());
		if(sp==null) {
			return null;
		}
		HCmsSpecialListVo spVo=new HCmsSpecialListVo();
		spVo.setLogoUrl(getLogUrls( sp));
		try {
			BeanUtils.copyProperties(spVo, sp);
		} catch (Exception e) {
			return null;
		}
		
		Long spId=null;
		List<CmsSpecial> childSpList=loadByParentId(queryDto.getSpecialId());
		if(childSpList==null||childSpList.isEmpty()) {//没有子专题，--直接挂新闻的类型
			spId=sp.getId();
		}else {//有子专题。。。。。
			spId=childSpList.get(0).getId();
			
		}
		//挂载一条新闻
		HCmsQueryDto queryDtox = new HCmsQueryDto().setSpecialId(spId).setMemberId(queryDto.getMemberId())
				.setPageNo(queryDto.getPageNo()).setPageSize(1);
		
		List<CmsContentVo> cmsContentVoList =  getCmsContentService().loadCmsContentBySpecilId(queryDtox);
		
		if(cmsContentVoList==null||cmsContentVoList.isEmpty()) {
			spVo.setCmsContentVoList(Lists.newArrayList());
		}else {
			CmsContentVo vox= cmsContentVoList.get(0);
 			spVo.setCmsContentVoList(new ArrayList<CmsContentVo>() {{add(vox);}});
		}
		return spVo;
	}
	

//	@Override
//	public  List<HCmsSpecialListVo> loadCmsSpecialListVoByParentId(HCmsQueryDto queryDto) {
//		List<HCmsSpecialListVo> data=Lists.newArrayList();
//		CmsSpecial rootS=cmsSpecialMng.find(queryDto.getSpecialId());
//		HCmsSpecialListVo root=new HCmsSpecialListVo();
//		root.setLogoUrl(getLogUrls( rootS));
//		try {
//			BeanUtils.copyProperties(root, rootS);
//		} catch (Exception e) {
////			e.printStackTrace();
//			return data;
//		}
//		List<CmsSpecial> childSpecialList=cmsSpecialMng.loadByParentId(root.getSpecialId());
//		
//		if(childSpecialList==null||childSpecialList.isEmpty()) {
//			return data;
//		}
//		for(CmsSpecial childSpecial: childSpecialList) {
//			
//			HCmsSpecialListVo childVo = new HCmsSpecialListVo();
//			
//			childVo.setLogoUrl(getLogUrls( childSpecial));
//			
//			HCmsQueryDto childQueryDto = new HCmsQueryDto().setSpecialId(childSpecial.getId()).setMemberId(queryDto.getMemberId())
//					.setPageNo(queryDto.getPageNo()).setPageSize(1);
//			
//			
//			//此处只获取一条数据
//			List<CmsContentVo> childCmsContentVoForChild =  getCmsContentService().loadCmsContentBySpecilId(childQueryDto);
//			
//			try {
//				BeanUtils.copyProperties(childVo, childSpecial);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if(childCmsContentVoForChild==null||childCmsContentVoForChild.isEmpty()) {
//				childVo.setCmsContentVoList(Lists.newArrayList());
//			}else {
//				CmsContentVo vo= childCmsContentVoForChild.get(0);
//				childVo.setCmsContentVoList(new ArrayList<CmsContentVo>() {{add(vo);}});
//			}
//			data.add(childVo);
//		}
//		return data;
//	}
	
	@Override
	public  HCmsSpecialVo loadCmsSpecialVoById(HCmsQueryDto queryDto) {
		
		CmsSpecial rootS=loadSpecialView(queryDto.getSpecialId());
		
		HCmsSpecialVo root=new HCmsSpecialVo();
		root.setLogoUrl(getLogUrls( rootS));
		
		try {
			BeanUtils.copyProperties(root, rootS);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		List<CmsContentVo> cmsContentVoForRoot =  CmsContentService.loadCmsContentBySpecilId(queryDto);
//		
//		root.setCmsContentVoList(cmsContentVoForRoot);
		
		List<CmsSpecial> childSpecialList=cmsSpecialMng.loadByParentId(root.getSpecialId());
		
		if(childSpecialList==null||childSpecialList.isEmpty()) {//直接挂载新闻列表的情况
			HCmsQueryDto queryDtox = new HCmsQueryDto().setSpecialId(root.getSpecialId()).setMemberId(queryDto.getMemberId())
					.setPageNo(queryDto.getPageNo()).setPageSize(queryDto.getPageSize());
			List<CmsContentVo> cmsContentVoForRoot =  getCmsContentService().loadCmsContentBySpecilId(queryDtox);
			root.setCmsContentVoList(cmsContentVoForRoot);
			return root;
		}
		//有子专题的情况
		for(CmsSpecial childSpecial: childSpecialList) {
			
			HCmsQueryDto childQueryDto = new HCmsQueryDto().setSpecialId(childSpecial.getId()).setMemberId(queryDto.getMemberId())
					.setPageNo(queryDto.getPageNo()).setPageSize(queryDto.getPageSize());
			//此处只获取一条数据
			List<CmsContentVo> childCmsContentVoForChild =  getCmsContentService().loadCmsContentBySpecilId(childQueryDto);
			
			HCmsSpecialVo childVo = new HCmsSpecialVo();
			try {
				BeanUtils.copyProperties(childVo, childSpecial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(childCmsContentVoForChild==null||childCmsContentVoForChild.isEmpty()) {
				childVo.setCmsContentVoList(null);
			}else {
				childVo.setCmsContentVoList(childCmsContentVoForChild);
			}
			root.addChildSpecialVo(childVo);
		}
		
		return root;
	}
	
	 @Scheduled(fixedDelay = 1000*60*5)
	   @CacheEvict(value = "cms_loadFirstLeveSpecialList",allEntries=true)
	   public void loadFirstLeveSpecialList_delete_from_cache() {
		   log.debug("----loadFirstLeveSpecialList_delete_from_cache------------");
	   }
	 @Cacheable(value = "cms_loadFirstLeveSpecialList")
	@Override
	public List<CmsSpecial> loadFirstLeveSpecialList(Short status){
		return cmsSpecialMng.loadFirstLeveSpecialList(status);
	}
	 
	 @CacheEvict(value = "cms_loadNotTopLeveSpecialList",allEntries=true)
	   @Scheduled(fixedDelay = 1000*60*5)//缓存5分钟，执行清理操作
	   public void loadNotTopLeveSpecialList_delete_from_cache() {
		   log.debug("----loadNotTopLeveSpecialList_delete_from_cache------------");
	   }
	 @Cacheable(value = "cms_loadNotTopLeveSpecialList")
	 @Override
	 public List<CmsSpecial> loadNotTopLeveSpecialList(Short status){
		 log.debug("----------loadNotTopLeveSpecialList-------");
			return cmsSpecialMng.loadNotTopLeveSpecialList(status);
		}
		 
	 @CacheEvict(value = "cms_loadByParentId",allEntries=true)
	   @Scheduled(fixedDelay = 1000*60*5)
	   public void loadByParentId_delete_from_cache() {}
	 
	 @Cacheable(value = "cms_loadByParentId",key="'loadByParentId_'+#parentId")
	@Override
	public  List<CmsSpecial> loadByParentId(Long parentId) {
		if(parentId==null) {
			return Lists.newArrayList();
		}
		return cmsSpecialMng.loadByParentId(parentId);
	}
	
	 @CacheEvict(value = "cms_loadSpecialView",allEntries=true)
	   @Scheduled(fixedDelay = 1000*60*5)//缓存5分钟，执行清理操作
	   public void loadSpecialView_delete_from_cache() {}
	 
	@Cacheable(value = "cms_loadSpecialView",key="'cms_loadSpecialView_'+#specialId")
	@Override
	public CmsSpecial loadSpecialView(Long specialId) {
		
		return cmsSpecialMng.find(specialId);
	}

	 @CacheEvict(value = "cms_loadCmsCateIdsBySpecialId",allEntries=true)
	   @Scheduled(fixedDelay = 1000*60*2)//缓存5分钟，执行清理操作
	   public void loadCmsCateIdsBySpecialId_delete_from_cache() {}
	
	@Cacheable(value = "cms_loadCmsCateIdsBySpecialId",key="'loadCmsCateIdsBySpecialId_'+#specialId")
	@Override
	public List<Long> loadCmsCateIdsBySpecialId(Long specialId) {
		List<CmsSpecialRCategory> list= cmsSpecialRCategoryMng.loadBySpecialId(specialId);
		if(list!=null&&!list.isEmpty()) {
			return list.stream().map(val->val.getCategoryId()).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}


	// 加载专题详细信息
	@Override
	public HCmsSpecialVo loadSpecialDetailForShare(HCmsQueryDto queryDto) {

		CmsSpecial rootS = loadSpecialView(queryDto.getSpecialId());
		// 子栏目不能分享
		if (Objects.isNull(rootS) || Objects.nonNull(rootS.getParent())) {
			return null;
		}

		HCmsSpecialVo root = new HCmsSpecialVo();
		root.setLogoUrl(getLogUrls(rootS));// 封面轮播图

		try {
			BeanUtils.copyProperties(root, rootS);// 专题信息
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 加载专题新闻类别
		List<CmsCategory> cmsCategoryList = Lists.newArrayList();
		List<Long> categoryIds = cmsSpecialRCategoryMng.loadCmsCateIdsBySpecialId(queryDto.getSpecialId());
		for (Long categoryId : categoryIds) {
			CmsCategory cmsCategory = cmsCategoryMng.find(categoryId);
			cmsCategoryList.add(cmsCategory);
		}
		root.setCmsCategoryList(cmsCategoryList);
		// 子栏目 栏目可直接查新闻 或者 子栏目下查新闻
		List<CmsSpecial> childSpecialList = cmsSpecialMng.loadByParentId(root.getSpecialId());

		if (childSpecialList == null || childSpecialList.isEmpty()) {// 没有子栏目时
			// 加载父栏目下的新闻 根据栏目分类查询新闻
			HCmsQueryDto childQueryDto = new HCmsQueryDto().setSpecialId(root.getSpecialId())
					.setPageNo(queryDto.getPageNo()).setPageSize(queryDto.getPageSize());
			List<CmsContentVo> cmsContentVoForChild = getCmsContentService().loadCmsContentBySpecilId(childQueryDto);
			root.setCmsContentVoList(cmsContentVoForChild);
			return root;
		}
		for (CmsSpecial childSpecial : childSpecialList) {

			HCmsQueryDto childQueryDto = new HCmsQueryDto().setSpecialId(childSpecial.getId())
					.setPageNo(queryDto.getPageNo()).setPageSize(queryDto.getPageSize());
			
			List<CmsContentVo> childCmsContentVoForChild = getCmsContentService()
					.loadCmsContentBySpecilId(childQueryDto);

			HCmsSpecialVo childVo = new HCmsSpecialVo();
			try {
				BeanUtils.copyProperties(childVo, childSpecial);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (childCmsContentVoForChild == null || childCmsContentVoForChild.isEmpty()) {//新闻isNull
				childVo.setCmsContentVoList(null);
			} else {
				childVo.setCmsContentVoList(childCmsContentVoForChild);
			}
			root.addChildSpecialVo(childVo);//添加子栏目list
		}
		return root;
	}
	 

}
