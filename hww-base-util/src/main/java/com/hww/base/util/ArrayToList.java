package com.hww.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组转List集合工具类
 * 
 * @author Kong.kenan
 *
 */
public class ArrayToList {

	/**
	 * 数组转Integer类型List
	 * 
	 * @param strArray
	 * @return
	 */
	public static List<Integer> intArrayToList(String[] strArray) {
		if (strArray.length == 0) {
			return null;
		}
		List<Integer> intList = new ArrayList<>();
		for (String str : strArray) {
			intList.add(Integer.parseInt(str));
		}
		return intList;
	}

	/**
	 * 数组转Integer类型List
	 * 
	 * @param strArray
	 * @return
	 */
	public static List<Long> longArrayToList(String[] strArray) {
		if (strArray.length == 0) {
			return null;
		}
		List<Long> longList = new ArrayList<>();
		for (String str : strArray) {
			longList.add(Long.parseLong(str));
		}
		return longList;
	}
}
