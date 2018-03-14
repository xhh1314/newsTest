package com.hww.cms.common.dao.impl;

import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.cms.common.vo.CmsCategoryVo;
import com.hww.cms.common.vo.CmsContentVo;
import com.hww.cms.common.vo.query.QueryContentVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.hww.cms.common.dao.CmsContentDao;
import com.hww.cms.common.entity.CmsCategory;
import com.hww.cms.common.entity.CmsContent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository("cmsContentDao")
public class CmsContentDaoImpl extends LocalDataBaseDaoImpl<Long, CmsContent> implements CmsContentDao {

    @Override
    public List<CmsContentVo> querySpecialNewList(CmsCategoryVo vo) {
        Finder finder = Finder.create("from CmsContent where 1=1");
        finder.append(" and categoryId=:categoryId");
        finder.append(" and auditStatus=1");
        finder.append(" order by releaseTime desc");
        finder.setParam("categoryId", vo.getCategoryId());
        finder.setMaxResults(vo.getPageSize());
        finder.setFirstResult((vo.getPageNo()-1)*vo.getPageSize());
        List<CmsContent> cmsContentList = (List<CmsContent>) find(finder);
        if (null == cmsContentList || cmsContentList.size() == 0) {
            return null;
        }
        List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
        return cmsContentVoList;
    }

    @Override
    public List<CmsContentVo> querySpecialNewForIndex(CmsCategoryVo vos) {
        Finder f = Finder.create("from CmsContent where 1=1");
        f.append(" and auditStatus=1");
        f.append(" and categoryId=:categoryId");
        f.append(" order by releaseTime desc");
        f.setParam("categoryId", vos.getCategoryId());
        f.setMaxResults(3);
        f.setFirstResult(0);
        List<CmsContent> cmsContentList = (List<CmsContent>) find(f);
        List<CmsContentVo> cmsContentVoList = BeanMapper.mapList(cmsContentList, CmsContentVo.class);
        return cmsContentVoList;
    }

    @Override
    public List<CmsContentVo> topCmsContentList() {
      
      Finder f = Finder.create("select title,content_id as contentId,content_type_id as contenttypeId,create_time as createTime,location,thumb_url as thumbUrl from cms_content");
      f.append(" where top = :top");
      f.append(" and auditstatus = :auditstatus");
      f.append(" order by create_time desc");
      f.setParam("top", 1);
      f.setParam("auditstatus", 1);
      List<CmsContentVo> list = (List<CmsContentVo>) findJoin(f, CmsContentVo.class);
      return list;
    }

    @Override
    public List<Map<String, Object>> cmsContentByColum(Integer columnId,Integer num) {
      Finder f = Finder.create("SELECT content_id as contentId,title,attachment_ids as attachmentIds,create_time as createTime,longitude,latitude,location,thumb_url as thumbUrl,content_type_id as contenttypeId,'1' as type");
      f.append(" FROM cms_column_category cc,cms_content con");
      f.append(" where cc.category_id = con.category_id");
      f.append(" and cc.column_id = :columnId");
      f.append(" and con.auditstatus = :auditstatus");
      f.append(" limit :pageStart,:pageSize");
      f.setParam("columnId", columnId);
      f.setParam("auditstatus", 1);
      f.setParam("pageStart", (num-1)*3);
      f.setParam("pageSize", 3);
      List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
      return list;
    }

    @Override
    public List<Map<String, Object>> cmsContentColum() {
      Finder f = Finder.create("SELECT column_id as columId,recomm_num as recommNum");
      f.append(" FROM cms_recomm_config");
      f.append(" where type = :type");
      f.setParam("type", 1);
      List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
      return list;
    }

    @Override
    public List<CmsContentVo> contentsBycategoryId(CmsContentVo vo) {
      
      Finder f = Finder.create("select title,content_id as contentId,create_time as createTime,location,thumb_url as thumbUrl,'1' as type from cms_content");
      f.append(" where category_id = :categoryId");
      f.append(" and auditstatus = :auditstatus");
      f.append(" order by create_time desc");
      f.append(" limit :pageStart,:pageSize");
      f.setParam("auditstatus", 1);
      f.setParam("categoryId", vo.getCategoryId());
      f.setParam("pageStart", (vo.getPageNo()-1)*vo.getPageSize());
      f.setParam("pageSize", vo.getPageSize());
      List<CmsContentVo> list = (List<CmsContentVo>) findJoin(f, CmsContentVo.class);
      return list;
    }

    @Override
    public String noInterests(QueryContentVo vo) {
      Finder f = Finder.create("select GROUP_CONCAT(content_id) AS contentId from cms_member_behavior");
      f.append(" where uuid = :imei");
      if(vo.getUserId()!=null) {
        f.append(" and member_id = :memberId");
        f.setParam("memberId", vo.getUserId());
      }
      f.setParam("imei", vo.getImei());
      List<Map<String, Object>> ma = (List<Map<String, Object>>) findJoin(f, Map.class);
      if(ma!=null&&ma.size()>0) {
        if(ma.get(0).get("contentId")!=null) {
          return ma.get(0).get("contentId").toString();
        }else {
          return null;
        }
      }
      return null;
    }

    @Override
    public List<CmsContentVo> cmsContentByColumId(QueryContentVo vo) {
    
    	   Finder finder = Finder.create("from ");
    	   //finder.append(CmsColumnCategory.class.getName());
    	   finder.append(" where column_id = :columnIdP");
    	   
    	   finder.setParam("columnIdP", vo.getColumnId());
    	   
		/*List<CmsColumnCategory> categorys = (List<CmsColumnCategory>) find(finder);
    	   
    	   List<Long> categoryIds=new ArrayList<>();
    	   
    	   if (categorys.size()>0) {
    		   for (CmsColumnCategory category:categorys ) {
    			   categoryIds.add(category.getCategoryId());
        	   }
    	   }*/
    	   
      Finder f = Finder.create(Finder.FROM);
      f.append(CmsContent.class.getName());
      f.append("where 1=1");
      if(StringUtils.isNotEmpty(vo.getNoInters())) {
        f.append(" and content_id not in ("+vo.getNoInters()+")");
      }
      /*if(categoryIds.size()>0) {
    	  f.append(" and category_id in (:categoryId)").setParamList("categoryId",categoryIds);
      }*/
      
      //-2表示后台删除
      f.append(" and status!=-2");
      
      f.append(" order by createTime desc");
      Pagination  pagination  = this.find(f,vo.getPageNo(),vo.getPageSize());
      List<CmsContentVo> list = new LinkedList<CmsContentVo>();
      if(pagination.getList().size()>0) {
    	  list = BeanMapper.mapList(pagination.getList(), CmsContentVo.class);
      }
      
      return list;
    }

    @Override
    public List<Map<String, Object>> cmsContentByColums(CmsContentVo vo) {
        Finder f = Finder.create("SELECT ct.content_id as contentId,ct.title,ct.create_time as createTime,ct.longitude,ct.latitude,ct.location,ct.thumb_url as thumbUrl,attachment_ids as attachmentIds,ct.content_type_id as contenttypeId,'1' as type");
        f.append(" FROM cms_recomm_config crc,cms_column_category cc, cms_content ct WHERE crc.column_id = cc.column_id AND cc.category_id = ct.category_id");
        f.append(" and crc.type = :type");
        f.append(" and ct.auditstatus = :auditstatus");
        f.append(" GROUP BY ct.content_id");
        f.append(" limit :pageStart,:pageSize");
        f.setParam("auditstatus", 1);
        f.setParam("type", 1);
        f.setParam("pageStart", (vo.getPageNo()-1)*vo.getPageSize());
        f.setParam("pageSize", vo.getPageSize());
        List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
        return list;
    }

    @Override
    public List<CmsContentVo> nearContents(CmsContentVo vo) {
        
        Finder f = Finder.create("select title,content_id as contentId,create_time as createTime,location,content_type_id as contenttypeId,thumb_url as thumbUrl,(round(6367000 * 2 * asin(sqrt(pow(sin(((latitude * pi()) / 180 - (:ulat * pi()) / 180) / 2), 2) + cos((:ulat * pi()) / 180) * cos((latitude * pi()) / 180) * pow(sin(((longitude * pi()) / 180 - (:ulon * pi()) / 180) / 2), 2))))) AS distance from cms_content");
        f.append(" where auditstatus = :auditstatus");
        f.append(" order by distance desc");
        f.append(" limit :pageStart,:pageSize");
        f.setParam("auditstatus", 1);
        f.setParam("ulat", vo.getLatitude());
        f.setParam("ulon", vo.getLongitude());
        f.setParam("pageStart", (vo.getPageNo()-1)*vo.getPageSize());
        f.setParam("pageSize", 2);
        List<CmsContentVo> list = (List<CmsContentVo>) findJoin(f, CmsContentVo.class);
        return list;
    }

	@Override
	public CmsContent findOneByContentId(Long contentId) {
		Finder finder = Finder.create("from CmsContent where 1=1");
		//finder.append(" and auditStatus=1");
		finder.append(" and contentId=:contentId");
		finder.setParam("contentId", contentId);
		List<CmsContent> cmsContentList = (List<CmsContent>) find(finder);
		if (null == cmsContentList || cmsContentList.size() == 0) {
			return null;
		}
		return cmsContentList.get(0);
	}
}
