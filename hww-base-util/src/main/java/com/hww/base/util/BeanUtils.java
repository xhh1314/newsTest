package com.hww.base.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.ConvertUtils;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
{
    static
    {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(
                new org.apache.commons.beanutils.converters.SqlDateConverter(
                        null), java.sql.Date.class);
        ConvertUtils.register(
                new org.apache.commons.beanutils.converters.SqlDateConverter(
                        null), java.util.Date.class);
        ConvertUtils
                .register(
                        new org.apache.commons.beanutils.converters.SqlTimestampConverter(
                                null), java.sql.Timestamp.class);
    }

    public static void copyProperties(Object target, Object source)
            throws InvocationTargetException, IllegalAccessException
    {
        // 支持对日期copy
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

    }
    
}
