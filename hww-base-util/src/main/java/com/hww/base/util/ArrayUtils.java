package com.hww.base.util;
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils
{
    public static Integer[] convertStrArrayToInt(String[] strArray)
    {
        if (strArray != null && strArray.length > 0)
        {
            Integer array[] = new Integer[strArray.length];
            for (int i = 0; i < strArray.length; i++)
            {
                array[i] = Integer.parseInt(strArray[i]);
            }
            return array;
        } else
        {
            return null;
        }
    }
}
