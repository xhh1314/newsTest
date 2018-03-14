package com.hww.base.common.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * 统一定义的entity基类.
 */
// JPA基类标识
@MappedSuperclass
public abstract class NullBaseEntity<ID extends Serializable> implements IBaseEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
