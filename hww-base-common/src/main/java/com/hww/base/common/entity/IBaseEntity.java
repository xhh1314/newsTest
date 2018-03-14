package com.hww.base.common.entity;

import java.io.Serializable;
/**
 * 基础接口
 * 对entity进行的基础定义和限制
 * @param <ID>
 */
public interface IBaseEntity<ID extends Serializable> extends Serializable
{
	//定义一个获取key值的通用方法
	public ID getId();
}
