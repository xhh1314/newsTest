package com.hww.cms.webadmin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件
 * 接收application.yml中的myProps下面的属性 
 * String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要 
 * @author hewei
 *
 */
@Component
@ConfigurationProperties(prefix="custom") 
public class MyProps {

	public static String picUploadLocation; //图片上传路径
	private String[] arrayProps; 
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值 
	private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值 
	private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
	public String getPicUploadLocation() {
		return picUploadLocation;
	}
	public void setPicUploadLocation(String picUploadLocation) {
		this.picUploadLocation = picUploadLocation;
	}
	public String[] getArrayProps() {
		return arrayProps;
	}
	public void setArrayProps(String[] arrayProps) {
		this.arrayProps = arrayProps;
	}
	public List<Map<String, String>> getListProp1() {
		return listProp1;
	}
	public void setListProp1(List<Map<String, String>> listProp1) {
		this.listProp1 = listProp1;
	}
	public List<String> getListProp2() {
		return listProp2;
	}
	public void setListProp2(List<String> listProp2) {
		this.listProp2 = listProp2;
	}
	public Map<String, String> getMapProps() {
		return mapProps;
	}
	public void setMapProps(Map<String, String> mapProps) {
		this.mapProps = mapProps;
	}
	
	
}
