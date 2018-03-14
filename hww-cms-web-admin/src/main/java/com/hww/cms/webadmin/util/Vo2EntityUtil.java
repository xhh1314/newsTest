package com.hww.cms.webadmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class Vo2EntityUtil
{
    public static void copyBean(Object src, Object dest)
    {
        // 此处只有一个数据类型有问题，先搞定这个
        ConvertUtils.register(new Converter()
        {
            @Override
            public Object convert(Class type, Object value)
            {
                if (value == null)
                    return null;
                String str = (String) value;
                if (str.trim().equals(""))
                    return null;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                    return df.parse(str.trim());
                } catch (ParseException e)
                {
                    throw new RuntimeException();
                }
            }
        }, Date.class);
        try
        {
            BeanUtils.copyProperties(dest, src);
        } catch (Exception e)
        {
            throw new RuntimeException();
        }
    }
}