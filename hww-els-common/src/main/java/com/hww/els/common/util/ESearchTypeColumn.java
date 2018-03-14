package com.hww.els.common.util;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 构建为elasticsearch
 * 方便使用的jsonBuilder对象
 *@author huangcheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ESearchTypeColumn {
    /**
    * 字段类型
    *
    * @return
    */
   String type() default "string";
 
   /**
    * 是否分词
    *
    * @return
    */
   boolean analyze() default false;
 
}
