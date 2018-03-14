package com.hww.cms.webadmin.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hww.framework.common.idgen.SnowFlakeIdGenerator;

@Configuration
public class SnowFlaConfig implements InitializingBean{


	@Autowired
	private Environment env;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		String app_id=env.getProperty("snowFlake.datacenter_id");
		String cluster_id=env.getProperty("snowFlake.cluster_id");
		try {
			SnowFlakeIdGenerator.setApp_id(Integer.parseInt(app_id));
			SnowFlakeIdGenerator.setCluster_id(Integer.parseInt(cluster_id));
		}catch (Exception e) {
			throw new RuntimeException("snowFlakeId配置错误");
		}
	}
	

}
