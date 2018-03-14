package com.hww.cms.common.dao.impl;

//import com.hww.base.common.util.Finder;
//import com.hww.cms.common.dao.CmsSpecialContentDao;
//import com.hww.cms.common.dao.CmsSpecialDao;
//import com.hww.cms.common.entity.CmsSpecial;
//import com.hww.cms.common.entity.CmsSpecialContent;
//import com.hww.cms.common.entity.CmsUserColumn;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository("cmsSpecialContentDao")
//public class CmsSpecialContentDaoImpl extends LocalDataBaseDaoImpl<Long, CmsSpecialContent>
//implements CmsSpecialContentDao {
//
//    @Override
//    public List<CmsSpecialContent> querySpecialContentList(Long specialId) {
//        Finder f = Finder.create("from CmsSpecialContent where 1=1");
//        f.append(" and specialId=:specialId");
//        f.append(" and status=1");
//        f.setParam("specialId", specialId);
//        List<CmsSpecialContent> list = (List<CmsSpecialContent>) find(f);
//        return list;
//    }
//
//    @Override
//    public CmsSpecialContent queryNewsBySpecialContentId(Long specialContentId) {
//        Finder f = Finder.create("from CmsSpecialContent where 1=1");
//        f.append(" and specialContentId=:specialContentId");
//        f.append(" and status=1");
//        f.setParam("specialContentId", specialContentId);
//        List<CmsSpecialContent> list = (List<CmsSpecialContent>) find(f);
//        if (list.isEmpty()) {
//            return null;
//        }
//        return list.get(0);
//    }
//}
