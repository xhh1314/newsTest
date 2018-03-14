package com.hww.framework.common.idgen;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import com.hww.base.util.SnowFlake;

/**
 * SnowFlakeIdGenerator
 * @author root
 *
 */

public class SnowFlakeIdGenerator implements IdentifierGenerator{

	private static SnowFlake snowFlake = null;
	
	private static int app_id=-1;
	private static int cluster_id=-1;
	
	public static int getApp_id() {
		return app_id;
	}


	public static void setApp_id(int app_id) {
		SnowFlakeIdGenerator.app_id = app_id;
	}


	public static int getCluster_id() {
		return cluster_id;
	}


	public static void setCluster_id(int cluster_id) {
		SnowFlakeIdGenerator.cluster_id = cluster_id;
	}


	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		return initSnowFlake(session).nextId();
	}


	public SnowFlakeIdGenerator() {
		super();
	}


	private SnowFlake initSnowFlake(SessionImplementor session) {
		
		if(snowFlake==null) {
			if(cluster_id<0||app_id<0) {
				throw new RuntimeException("snowFlake 初始化错误");
			}
			 snowFlake= new SnowFlake(cluster_id, app_id);
		}
		return snowFlake;
	}


}
