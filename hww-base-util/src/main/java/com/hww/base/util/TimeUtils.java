package com.hww.base.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kongkenan on 2017/11/21.
 */
public class TimeUtils {

    /**
     * 将timestamp转换成长string
     * @param timestamp
     * @return
     */
    public static String timeConverter(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    public static String simpleTimeConverter(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        return sdf.format(timestamp);
    }

    /**
     * 将timestamp转换成短string
     * @param timestamp
     * @return
     */
    public static String shortTimeConverter(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

    /**
     * 由过去的某一时间,计算距离当前的时间
     * */
    public static String calculateTime(Timestamp timestamp){
        long nowTime=System.currentTimeMillis();  //获取当前时间的毫秒数
        String msg = null;
        long reset=timestamp.getTime();   //获取指定时间的毫秒数
        long dateDiff=nowTime-reset;
        if(dateDiff<0){
            msg="输入的时间不对";
        }else{
            long dateTemp1=dateDiff/1000; //秒
            long dateTemp2=dateTemp1/60; //分钟
            long dateTemp3=dateTemp2/60; //小时
            long dateTemp4=dateTemp3/24; //天数
            long dateTemp5=dateTemp4/30; //月数
            long dateTemp6=dateTemp5/12; //年数
            if(dateTemp6>0){
                msg = dateTemp6+"年前";

            }else if(dateTemp5>0){
                msg = dateTemp5+"个月前";

            }else if(dateTemp4>0){
                msg = dateTemp4+"天前";

            }else if(dateTemp3>0){
                msg = dateTemp3+"小时前";

            }else if(dateTemp2>0){
                msg = dateTemp2+"分钟前";

            }else if(dateTemp1>0){
                msg = "刚刚";
            }
        }
        return msg;
    }

    public static Timestamp dateConvertToTimeStamp(Calendar calendar, int hours) {
        calendar.add(Calendar.HOUR, hours);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
        return timestamp;
    }

    public static Timestamp getDateToTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
