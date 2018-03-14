package com.hww.base.util;

import java.util.*;

import com.alibaba.fastjson.JSON;
import org.dozer.DozerBeanMapper;

public class BeanMapper {
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 构造新的destinationClass实例对象，通过source对象中的字段内容
	 * 映射到destinationClass实例对象中，并返回新的destinationClass实例对象。
	 * 
	 * @param source
	 *            源数据对象
	 * @param destinationClass
	 *            要构造新的实例对象Class
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		if (source != null) {
			return dozer.map(source, destinationClass);
		}
		return null;
	}

	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList();
		for (Iterator i = sourceList.iterator(); i.hasNext();) {
			Object sourceObject = i.next();
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * 将对象source的所有属性值拷贝到对象destination中.
	 * 
	 * @param source
	 *            对象source
	 * @param destination
	 *            对象destination
	 */
	public static void copy(Object source, Object destinationObject) {
		dozer.map(source, destinationObject);
	}

	/**
	 * 把bean实例转换成Map<String,String> ,方便redis存储
	 * @param obj
	 * @return
	 */
	public static Map<String, String> mapBeanToStringMap(Object obj) {
		Map<String, String> map = new HashMap<>(32);
		Map<String, Object> temp = JSON.parseObject(JSON.toJSONString(obj), Map.class);
		temp.forEach((s, o) -> {
			if (o instanceof String) {
				map.put(s, (String) o);
			} else {
				map.put(s, o.toString());
			}
		});
		return map;
	}

	/** 把Map<String,String>转换成Bean
	 * @param map
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T mapToBean(Map<String, String> map, Class<T> type) {
		return JSON.parseObject(JSON.toJSONString(map), type);
	}

}
