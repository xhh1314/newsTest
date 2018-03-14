package com.hww.base.common.listener;

import java.util.Map;

import com.hww.base.common.entity.IBaseEntity;

public class ModifyListenerAdapter<ENTITY extends IBaseEntity<?>> implements IModifyListener<ENTITY>
{

	@Override
	public void preSave(ENTITY entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSave(ENTITY entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> preChange(ENTITY entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterChange(ENTITY entity, Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preDelete(ENTITY entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterDelete(ENTITY entity) {
		// TODO Auto-generated method stub
		
	}

}
