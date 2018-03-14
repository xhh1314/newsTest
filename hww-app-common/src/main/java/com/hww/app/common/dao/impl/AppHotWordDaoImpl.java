package com.hww.app.common.dao.impl;

import com.hww.app.common.dao.AppHotWordDao;
import com.hww.app.common.dto.AppHotWordDto;
import com.hww.app.common.entity.AppHotWord;
import com.hww.app.common.vo.AppHotWordVo;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.base.util.StringUtils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("appHotWordDao")
public class AppHotWordDaoImpl extends LocalEntityDaoImpl<Long, AppHotWord> implements AppHotWordDao {

   @Override
    public Pagination list(AppHotWordDto searchDto, Integer pageNo, Integer pageSize) {
        Finder finder = Finder.create("from AppHotWord where 1=1");
        if (searchDto.getHotId() != null) {
            finder.append(" and hotId=:hotId");
            finder.setParam("hotId", searchDto.getHotId());
        }
        if (StringUtils.isNotBlank(searchDto.getHotWord())) {
            finder.append(" and hotWord=:hotWord");
            finder.setParam("hotWord", searchDto.getHotWord());
        }
        if (searchDto.getType() != null) {
            finder.append(" and type=:type");
            finder.setParam("type", searchDto.getType());
        }
        finder.append(" order by createTime desc");
        Pagination p = find(finder, pageNo, pageSize);
        if (p != null && p.getList() != null) {
            List<AppHotWordVo> cmsHotWordDtoList = BeanMapper.mapList(p.getList(), AppHotWordVo.class);
            p.setList(cmsHotWordDtoList);
        }
        return p;
    }

    @Override
    public void saveHotWord(AppHotWordDto cmsHotWordDto) {
    	AppHotWord hotWord = BeanMapper.map(cmsHotWordDto, AppHotWord.class);
        save(hotWord);
    }

    @Override
    public boolean queryHotWordByHotWordAndType(AppHotWordDto cmsHotWordDto) {
        Finder finder = Finder.create("from AppHotWord where 1=1");
        finder.append(" and hotWord=:hotWord");
        finder.append(" and type=:type");
        finder.setParam("hotWord", cmsHotWordDto.getHotWord());
        finder.setParam("type", cmsHotWordDto.getType());
        List<AppHotWord> cmsHotWordList = (List<AppHotWord>) find(finder);
        if (null == cmsHotWordList || cmsHotWordList.size() == 0) {
            return false;
        }
        return true;
    }
    

	
    @Override
    public List<AppHotWord> searchHotWord() {
        Finder finder = Finder.create("from AppHotWord where 1=1");
        finder.append(" and auditStatus=1");
        finder.append(" order by createTime desc");
        finder.setFirstResult(0);
        finder.setMaxResults(10);
        List<AppHotWord> searchHotWordList = (List<AppHotWord>) find(finder);
        return searchHotWordList;
    }
}
