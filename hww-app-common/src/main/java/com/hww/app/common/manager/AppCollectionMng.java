package com.hww.app.common.manager;

import com.hww.app.common.dao.AppCollectionDao;
import com.hww.app.common.dao.AppMemberBehaviorDao;
import com.hww.app.common.entity.AppCollection;
import com.hww.app.common.entity.AppMemberBehavior;
import com.hww.app.common.vo.AppMemberBehaviorVo;
import com.hww.base.common.manager.IBaseEntityMng;

import java.util.List;

public interface AppCollectionMng extends IBaseEntityMng<Long, AppCollection, AppCollectionDao> {
	
}
