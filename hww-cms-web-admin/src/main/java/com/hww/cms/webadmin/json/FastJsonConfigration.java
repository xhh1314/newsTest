/**
 * Copyright (c) 2016-2020 https://github.com/zhaohuatai
 *
 * contact 824069438@qq.com
 *  
 */
package com.hww.cms.webadmin.json;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
@Configuration
@ConditionalOnClass({JSON.class})
public class FastJsonConfigration {
	

	@Bean
	public HttpMessageConverter<?> fastJsonHttpMessageConverter() {
	       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
	       FastJsonConfig fastJsonConfig = new FastJsonConfig();
	      
	       fastJsonConfig.setSerializerFeatures(initSerializerFeature( ));
	       
//	       fastJsonConfig.setDateFormat(fastJsonProperties.getDateFormat());
	       
	       fastJsonConfig.setSerializeFilters( initSerializeFilters());
	       
	       
	       fastConverter.setFastJsonConfig(fastJsonConfig);
	       HttpMessageConverter<?> converter = fastConverter;
	       
	       List<MediaType> mediaTypeList=  converter.getSupportedMediaTypes();
	       List newList = new ArrayList(mediaTypeList); 
	       newList.set(0, MediaType.APPLICATION_JSON);
	       newList.add(MediaType.APPLICATION_JSON_UTF8);
	       newList.add(MediaType.APPLICATION_XML);
	       newList.add(MediaType.IMAGE_PNG);
	       fastConverter.setSupportedMediaTypes(newList);
	       
	       return converter;
	    }
	private  SerializerFeature[] initSerializerFeature() {
		 List<SerializerFeature> featrues=Lists.newArrayList();
	       
		 featrues.add(SerializerFeature.WriteMapNullValue);
	       
		 featrues.add(SerializerFeature.WriteNullListAsEmpty);
	       
		 featrues.add(SerializerFeature.QuoteFieldNames);
		  featrues.add(SerializerFeature.WriteNullStringAsEmpty);
	       
		  featrues.add(SerializerFeature.WriteDateUseDateFormat);
	       
		  featrues.add(SerializerFeature.DisableCircularReferenceDetect);
	       featrues.add(SerializerFeature.PrettyFormat);
	       
	       SerializerFeature[] serializerFeatureArray = new SerializerFeature[featrues.size()];
	       featrues.toArray(serializerFeatureArray);
	       return serializerFeatureArray;
	}
	
	 public SerializeFilter[] initSerializeFilters(){
		List<SerializeFilter> filters=new ArrayList<>();
		filters.add(new FastJsonBigNumFilter());
		SerializeFilter[] filterArrays= new SerializeFilter[filters.size()];
		filters.toArray(filterArrays);
		return filterArrays;
	}
}
