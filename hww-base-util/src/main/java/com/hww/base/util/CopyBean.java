package com.hww.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * 复制类属性值
 *
 * @author yanxin
 * @email 798823035@qq.com
 * @date 2017年11月6日 下午3:21:00
 * @version v0.1
 */
public class CopyBean {
  /**
   * 复制sour里属性不为空的值到obje为空的属性
   * 
   * @param sour 源实体类
   * @param obje 目标实体类
   * @param isCover 是否保留obje类里不为null的属性值(保留源值，属性为null则赋值)
   *
   * @author yanxin
   * @email 798823035@qq.com
   * @date 2017年11月6日 下午3:20:44
   * @return 
   * @version v0.1
   */
  public static Object Copy(Object sour, Object obje, boolean isCover) {
    Field[] fields = sour.getClass().getDeclaredFields();
    for (int i = 0, j = fields.length; i < j; i++) {
      String propertyName = fields[i].getName();
      Object propertyValue = getProperty(sour, propertyName);
      if (isCover) {
        if (getProperty(obje, propertyName) == null && propertyValue != null) {
          Object setProperty = setProperty(obje, propertyName, propertyValue);
        }
      } else {
        Object setProperty = setProperty(obje, propertyName, propertyValue);
      }

    }
    return obje;
  }
  
  /**
   * 拷贝source不为空的属性到target,更新常用
   * @param sour
   * @param obje
   * @return
   */
  public static void copyNotNull(Object source, Object target) {
	    Field[] fields = source.getClass().getDeclaredFields();
	    for (int i = 0, j = fields.length; i < j; i++) {
	      String propertyName = fields[i].getName();
	      Object propertyValue = getProperty(source, propertyName);
	      if(propertyValue!=null) {
	    	  setProperty(target, propertyName, propertyValue);
	      }
	    }
  }
  
  
  /**
   * request.getParameterMap() Map<String, String[]>
   * 复制属性到bean
   * @param source
   * @param target
   * @return
   */
  public static void copy(Map source,Object target) {
	  if(source==null||target==null) {
		  return;
	  }
	  Iterator iter = source.keySet().iterator();
	  while(iter.hasNext()) {
		  try {
			  Object key = iter.next();
			  Object value = source.get(key);
			  if(key!=null&&value!=null) {
				  if(value instanceof String[]) {
					  String[] values = (String[]) value;
					  if(values.length>0) {
						  setProperty(target, key.toString(), values[0]);
					  }
				  }
				  
			  }
		  }catch(Exception e) {
			  
		  }
	  }
  }

  /**
   * 得到值
   * 
   * @param bean
   * @param propertyName
   * @return
   */
  private static Object getProperty(Object bean, String propertyName) {
    Class clazz = bean.getClass();
    try {
      Field field = clazz.getDeclaredField(propertyName);
      Method method = clazz.getDeclaredMethod(getGetterName(field.getName()), new Class[] {});
      return method.invoke(bean, new Object[] {});
    } catch (Exception e) {
    }
    return null;
  }

  /**
   * 给bean赋值
   * 
   * @param bean
   * @param propertyName
   * @param value
   * @return
   */
  private static Object setProperty(Object bean, String propertyName, Object value) {
    Class clazz = bean.getClass();
    try {
      Field field = clazz.getDeclaredField(propertyName);
      Method method =
          clazz.getDeclaredMethod(getSetterName(field.getName()), new Class[] {field.getType()});
      return method.invoke(bean, new Object[] {value});
    } catch (Exception e) {
    }
    return null;
  }

  /**
   * 根据变量名得到get方法
   * 
   * @param propertyName
   * @return
   */
  private static String getGetterName(String propertyName) {
    String method = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    return method;
  }

  /**
   * 得到setter方法
   * 
   * @param propertyName 变量名
   * @return
   */
  private static String getSetterName(String propertyName) {
    String method = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    return method;
  }
}
