package com.hww.file.common.dao.impl;

import com.hww.base.common.entity.IBaseEntity;
import com.hww.framework.common.dao.impl.JpaDaoImpl;

import java.io.Serializable;

//配置不同的sessionFactory用来实现不同数据库的访问，对上层透明
public class LocalEntityDaoImpl<ID extends Serializable, ENTITY extends IBaseEntity<ID>>
		extends
			JpaDaoImpl<ID, ENTITY> {



}
