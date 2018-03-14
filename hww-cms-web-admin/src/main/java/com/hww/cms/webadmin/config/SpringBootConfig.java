package com.hww.cms.webadmin.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import javax.servlet.MultipartConfigElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configuration
public class SpringBootConfig {

	private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private static final Log log = LogFactory.getLog(SpringBootConfig.class);
	
	@Bean
    public ConversionService conversionService() {
        FormattingConversionServiceFactoryBean factory = new FormattingConversionServiceFactoryBean();
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        factory.setFormatterRegistrars(Collections.singleton(registrar));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

	/**
	 * 图片上传配置
	 * picUploadLocation  配置文件配置
	 * @return
	 */
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(MyProps.picUploadLocation);
        return factory.createMultipartConfig();
    }

	public static class StringToDateConverter implements Converter<String, Date> {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		@Override
		public Date convert(String source) {
			Date date = null;
			try {
				if(StringUtils.isNotEmpty(source)) {
					date = sdf.parse(source);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
	}
}
