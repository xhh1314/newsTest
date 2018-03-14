package com.hww.cms.common.dao.impl;

import java.io.Serializable;

import com.hww.base.common.entity.IBaseEntity;
import com.hww.framework.common.dao.impl.JpaDaoImpl;

public class LocalDataBaseDaoImpl<ID extends Serializable, ENTITY extends IBaseEntity<ID>>
	extends
		JpaDaoImpl<ID, ENTITY> {

}
