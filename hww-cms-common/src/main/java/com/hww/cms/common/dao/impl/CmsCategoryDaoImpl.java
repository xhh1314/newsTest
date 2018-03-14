package com.hww.cms.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.BeanUtils;
import com.hww.base.util.StringUtils;
import com.hww.base.util.TimeUtils;
import com.hww.cms.common.entity.CmsContent;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;
import org.springframework.stereotype.Repository;

import com.hww.base.common.util.Finder;
import com.hww.cms.common.Constants;
import com.hww.cms.common.dao.CmsCategoryDao;
import com.hww.cms.common.entity.CmsCategory;

@Repository("cmsCategoryDao")
public class CmsCategoryDaoImpl extends LocalDataBaseDaoImpl<Long, CmsCategory> implements CmsCategoryDao {

	@Override
	public List<CmsCategory> getChildList(Long parentId, Short display) {
		Finder f = Finder.create("from CmsCategory bean");
		f.append(" where bean.parent.menuId=:parentId");
		f.setParam("parentId", parentId);

		if (display != null) {
			f.append(" and bean.display=:display");
			f.setParam("display", display);
		}
		f.append(" order by bean.priority asc,bean.menuId asc");
		return (List<CmsCategory>) find(f);
	}

	@Override
	public List<CmsCategory> getRetrievingFullTree(Long userId, Integer siteId, Short status) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("select bean from ").append(CmsCategory.class.getName()).append(" bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.siteId=:siteIdP").setParam("siteIdP", siteId);
		}
		/*if (status != null) {
			f.append(" and bean.status=:statusP").setParam("statusP", status);
		}*/
		if(status!=null) {
			f.append(" and bean.status=1");
		}else {
		f.append(" and bean.status>-1");
		}

		f.append("and bean.typeId!=:type_idP").setParam("type_idP", Constants.categoryType.special);
		f.append(" order by bean.priority asc");
		return (List<CmsCategory>) find(f);
	}

	@Override
	public List<CmsCategory> getRetrievingASinglePath(Integer categoryId, Integer siteId) {
		// TODO Auto-generated method stub
		Finder f = Finder.create("select parent from ").append(CmsCategory.class.getName()).append(" node,")
				.append(CmsCategory.class.getName())
				.append(" parent where  node.lft between parent.lft and parent.rgt and node.categoryId=:categoryIdP order by parent.lft")
				.setParam("categoryIdP", categoryId);
		return (List<CmsCategory>) find(f);
	}

	@Override
	public List<CmsCategoryVo> querySpecials() {
		Finder finder = Finder.create("from CmsCategory where 1=1");
		finder.append(" and typeId=3 and parent!=null");
		finder.append(" and auditStatus=1");
		List<CmsCategory> categoryList = (List<CmsCategory>) find(finder);
		if (null == categoryList || categoryList.size() == 0) {
			return null;
		}
		List<CmsCategoryVo> cmsCategoryVoList = BeanMapper.mapList(categoryList, CmsCategoryVo.class);
		return cmsCategoryVoList;
	}

	@Override
	public List<CmsCategoryVo> queryChildSpecialList(CmsCategoryVo vo) {
		Finder finder = Finder.create("from CmsCategory where 1=1");
		finder.append(" and typeId=3");
		finder.append(" and auditStatus=1");
		finder.append(" and parent.categoryId!=null");
		finder.append(" and parent.categoryId=:parentCategoryId");
		finder.setParam("parentCategoryId", vo.getCategoryId());
		List<CmsCategory> categoryList = (List<CmsCategory>) find(finder);
		List<CmsCategoryVo> cmsCategoryVos = BeanMapper.mapList(categoryList, CmsCategoryVo.class);
		return cmsCategoryVos;
	}

	@Override
	public List<CmsCategory> getCategorysByType(Short typeId, Integer siteId, Long parentId) {
		Finder f = Finder.create(Finder.FROM);
		f.append(CmsCategory.class.getName());
		f.append("where 1=1");
		if (typeId != null) {
			f.append("and typeId=:typeId").setParam("typeId", typeId);
		}
		if (parentId != null) {
			if (parentId == 0) {
				f.append("and parent=:parent").setParam("parent", null);
			} else {
				CmsCategory parent = new CmsCategory();
				parent.setCategoryId(parentId);
				f.append("and parent=:parent").setParam("parent", parent);
			}
		}
		f.append("and status=1");
		f.append("and siteId=:siteId").setParam("siteId", siteId);
		return (List<CmsCategory>) find(f);
	}

	@Override
	public List<CmsCategoryVo> categoryDetail(CmsCategoryVo vo) {

		Finder f = Finder
				.create("select category_id as categoryId,category_name as categoryName,logo from cms_category");
		f.append(" where parent_id = :categoryId");
		f.append(" and type_id = :typeId");
		f.append(" order by create_time asc");
		f.setParam("categoryId", vo.getCategoryId());
		f.setParam("typeId", 3);
		List<CmsCategoryVo> list = (List<CmsCategoryVo>) findJoin(f, CmsCategoryVo.class);
		return list;
	}

	@Override
	public List<CmsContentVo> cmsContentByCategoryId(CmsCategoryVo vo) {

		Finder f = Finder.create(
				"select content_id as contentId,title,category_id as categoryId,longitude,latitude,create_time as createTime,thumb_url as thumbUrl,location from cms_content");
		f.append(" where category_id = :categoryId");
		f.append(" order by create_time desc");
		f.append(" limit 0,3");
		f.setParam("categoryId", vo.getCategoryId());
		List<CmsContentVo> list = (List<CmsContentVo>) findJoin(f, CmsContentVo.class);
		return list;
	}

	@Override
	public List<Map<String, Object>> cmsCategoryNum() {
		Finder f = Finder.create("SELECT column_id as columId,recomm_num as recommNum,'2' as type");
		f.append(" FROM cms_recomm_config");
		f.append(" where type = :type");
		f.setParam("type", 2);
		List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
		return list;
	}

	@Override
	public List<Map<String, Object>> cmsCategorys(Integer num) {

		Finder f = Finder.create(
				"SELECT category_id as categoryId,category_name as categoryName,logo,create_time as createTime,'2' as type");
		f.append(" FROM cms_category");
		f.append(" where type_id = :typeId");
		f.append(" and parent_id is null");
		f.append(" limit :pageStart,:pageSize");
		f.setParam("typeId", 3);
		f.setParam("pageStart", 0);
		f.setParam("pageSize", num);
		List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
		return list;
	}

	@Override
	public List<Map<String, Object>> cmsTopicNum() {
		Finder f = Finder.create("SELECT column_id as columId,recomm_num as recommNum");
		f.append(" FROM cms_recomm_config");
		f.append(" where type = :type");
		f.setParam("type", 3);
		List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
		return list;
	}

	@Override
	public List<Map<String, Object>> cmsContentByCatId(Integer categoryId, Integer num) {

		Finder f = Finder.create(
				"select content_id as contentId,title,category_id as categoryId,attachment_ids as attachmentIds,longitude,latitude,create_time as createTime,thumb_url as thumbUrl,location,content_type_id as contenttypeId,'1' as type from cms_content");
		f.append(" where category_id = :categoryId");
		f.append(" and auditstatus = :auditstatus");
		f.append(" order by create_time desc");
		f.append(" limit :pageStart,:pageSize");
		f.setParam("auditstatus", 1);
		f.setParam("categoryId", categoryId);
		f.setParam("pageStart", 0);
		f.setParam("pageSize", 3);
		List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
		return list;
	}
}
