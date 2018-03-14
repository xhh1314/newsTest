
package com.hww.cms.webadmin.json;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 过大整数，js无法解析，直接将整数转化为字符串处理
 * @author zhaohuatai
 *
 */
public class FastJsonBigNumFilter implements ValueFilter{

	@Override
	public Object process(Object object, String name, Object value) {
		if(value==null){return value;}
		
		if(!(value instanceof java.lang.Long||value instanceof java.lang.Integer)){
			return value;
		}else{
			if((new Long(value.toString()))<99999999999999L){//js 支持到15位
				return value;
			}else{
				return ""+value;
			}
		}
		
	}

}
