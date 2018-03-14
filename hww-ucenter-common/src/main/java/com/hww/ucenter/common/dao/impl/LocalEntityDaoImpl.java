package com.hww.ucenter.common.dao.impl;

import java.io.Serializable;

import com.hww.base.common.entity.IBaseEntity;
import com.hww.framework.common.dao.impl.JpaDaoImpl;
//配置不同的sessionFactory用来实现不同数据库的访问，对上层透明
public class LocalEntityDaoImpl<ID extends Serializable, ENTITY extends IBaseEntity<ID>>
		extends
			JpaDaoImpl<ID, ENTITY> {

}
