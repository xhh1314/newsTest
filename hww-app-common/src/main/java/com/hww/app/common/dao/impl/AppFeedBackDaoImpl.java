package com.hww.app.common.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hww.app.common.dao.AppFeedBackDao;
import com.hww.app.common.dto.AppFeedBackDto;
import com.hww.app.common.entity.AppFeedBack;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;

@Repository
public class AppFeedBackDaoImpl extends LocalEntityDaoImpl<Long, AppFeedBack> implements AppFeedBackDao {
	public Pagination queryAppFeedBack(AppFeedBackDto dto ,Integer pageNo, Integer pageSize) {
		Finder finder=Finder.create("from AppFeedBack ");
		finder.append(" where 1=1 ");
		if(dto.getMemberId()!=null){
			finder.append(" and memberId=:memberId").setParam("memberId", dto.getMemberId());
		}
		if(dto.getCreateTime()!=null){
			finder.append(" and createTime=:createTime").setParam("createTime", dto.getCreateTime());
		}
		Pagination p = find(finder, pageNo, pageSize);
		return p;
	}
}
