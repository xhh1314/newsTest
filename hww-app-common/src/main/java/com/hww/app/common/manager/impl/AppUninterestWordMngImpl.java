package com.hww.app.common.manager.impl;

import com.hww.app.common.dao.AppUninterestWordDao;
import com.hww.app.common.entity.AppUninterestWord;
import com.hww.app.common.manager.AppUninterestWordMng;
import com.hww.base.common.manager.impl.BaseEntityMngImpl;
import com.hww.base.common.util.Finder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("appUninterestWordMng")
@Transactional
public class AppUninterestWordMngImpl extends BaseEntityMngImpl<Long, AppUninterestWord, AppUninterestWordDao> implements AppUninterestWordMng {

    @Autowired
    private AppUninterestWordDao appUninterestWordDao;

    @Autowired
    public void setQueryDao(AppUninterestWordDao appUninterestWordDao) {
        super.setEntityDao(appUninterestWordDao);
        this.appUninterestWordDao = appUninterestWordDao;
    }
    @Override
    public List<AppUninterestWord> loadAllUninterestWord(){
    	Finder f = Finder.create("from AppUninterestWord bean");
		f.append(" where bean.status=1 ");
		
		List<?> res=appUninterestWordDao.find(f);
		if(res==null||res.isEmpty()){
			return new ArrayList<AppUninterestWord>(0);
		}
		return (List<AppUninterestWord>) (res);
    }
    
  
}
