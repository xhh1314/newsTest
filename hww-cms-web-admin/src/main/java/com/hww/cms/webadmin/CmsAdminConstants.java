package com.hww.cms.webadmin;

import java.util.HashMap;
import java.util.Map;

/**
 * CMS常量
 * 
 */
public class CmsAdminConstants
{
	private static Map<String,Object> revertParams = null;
	
	public static Map<String,Object> getRevertParams() {
		if(revertParams==null) {
			revertParams = new HashMap<String,Object>();
			revertParams.put("callback", "renderReverse");
			revertParams.put("output", "json");
			revertParams.put("pois", "1");
			revertParams.put("ak", "40bcbd6c7361485243a9c1e30d60d6cc");
		}
		return revertParams;
	}

}
