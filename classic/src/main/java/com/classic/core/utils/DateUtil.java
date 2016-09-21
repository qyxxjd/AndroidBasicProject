package com.classic.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期操作工具类
 *
 * @author 续写经典
 * @version 1.0 2015/11/3
 */
public final class DateUtil {
    private DateUtil(){}
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
    public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("HH:mm",Locale.CHINA);

    private static final Calendar INSTANCE = Calendar.getInstance();
    public static int getYear(){
        return INSTANCE.get(Calendar.YEAR);
    }
    public static int getMonth(){
        return INSTANCE.get(Calendar.MONTH);
    }
    public static int getDay(){
        return INSTANCE.get(Calendar.DAY_OF_MONTH);
    }
    public static int getHours(){
        return INSTANCE.get(Calendar.HOUR_OF_DAY);
    }
    public static int getMinutes(){
        return INSTANCE.get(Calendar.MINUTE);
    }
    public static int getSeconds(){
        return INSTANCE.get(Calendar.SECOND);
    }

    /**
     * 获取N天前、N天后的 日期
     *
     * @param nowDate
     *            当前日期;
     * @param dayAddNum
     *            正数：N天前，负数：N天后;
     * @return
     */
    public static Date getAddDay(Date nowDate, int dayAddNum) {
        Calendar tday = new GregorianCalendar();
        tday.setTime(nowDate);
        tday.add(Calendar.DAY_OF_MONTH, dayAddNum);
        Date preDate = tday.getTime();
        return preDate;
    }
    /**
     * 获取N天前、N天后的 日期
     * @param nowDate  当前时间戳;
     * @param dayAddNum 正数：N天前，负数：N天后;
     * @return
     */
    public static Date getAddDay(long nowDate, int dayAddNum){
        return getAddDay(new Date(nowDate), dayAddNum);
    }

    /**
     * 时间格式化
     * @param format
     * @param time
     * @return
     */
    public static String formatDate(String format,Long time) {
        return formatDate(new SimpleDateFormat(format, Locale.CHINA), time);
    }
    /**
     * 时间格式化
     * @param format
     * @param time
     * @return
     */
    public static String formatDate(SimpleDateFormat format,Long time) {
        if(null==time || time<=0) return "";
        return format.format(new Date(String.valueOf(time).length() == 13 ? time : time * 1000));
    }

    /**
     * 将秒转换为（ 分：秒 格式）
     * @param time 单位：秒
     * @return
     */
    public static String getTimeFromInt(int time) {
        if (time <= 0) return "00:00";
        int secondnd = time / 60;
        int million = time % 60;
        String f = secondnd >= 10 ? String.valueOf(secondnd) : "0" + String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0" + String.valueOf(million);
        return f + ":" + m;
    }

    /**
     * 根据月份获得最大天数
     * @param year 年
     * @param month 月
     * @return 最大天数
     */
    public static int getMaxDayByMonth(int year,int month){
        Calendar time=Calendar.getInstance();//使用默认时区和语言环境获得一个日历
        //注意：在使用set方法之前，必须先调用clear（），否则很多信息会继承自系统当前的时间
        time.clear();
        time.set(Calendar.YEAR,year);
        time.set(Calendar.MONTH,month);//注意Calendar对象默认一月是为零的
        int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//获得本月份的天数
        return day;
    }

    /**
     * 根据日期获得星期
     */
    public static String getWeek(Date d) {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return (dayNames[dayOfWeek]);
    }

    /**
     * 时间格式化（刚刚、几分钟前、几小时前、昨天、前天、年-月-日）
     * @param time
     * @return
     */
    public static String getShortTime(long time) {
        String shortstring = "";
        if (time == 0)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long datetime = (now - time) / 1000;
        if (datetime > 365 * 24 * 60 * 60) {
            shortstring = FORMAT_DATE.format(new Date(time));
        } else if (datetime > 24 * 60 * 60 && ((int) (datetime / (24 * 60 * 60)))==2){
            shortstring = "前天";
        } else if (datetime > 24 * 60 * 60 && ((int) (datetime / (24 * 60 * 60)))==1){
            shortstring = "昨天";
        } else if (datetime > 60 * 60) {
            shortstring = (int) (datetime / (60 * 60)) + "小时前";
        } else if (datetime > 60) {
            shortstring = (int) (datetime / (60)) + "分钟前";
        } else {
            shortstring = "刚刚";
        }
        return shortstring;
    }

    /**
     * 时间格式化(秒，分，小时，天)
     * @param time
     * @return
     */
    public static String getShortTime2(long time) {
        String shortstring = "";
        if (time == 0)
            return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long datetime = (now - time) / 1000; // 秒
        if (datetime > 24 * 60 * 60) {
            shortstring = (int) (datetime / (24 * 60 * 60)) + "天";
        } else if (datetime > 60 * 60) {
            shortstring = (int) (datetime / (60 * 60)) + "小时";
        } else if (datetime > 60) {
            shortstring = (int) (datetime / (60)) + "分钟";
        } else {
            shortstring = (int) datetime + "秒";
        }
        return shortstring;
    }

    /**
     * 获取当前日期上一周的开始日期 （周日）
     */
    public static String previousWeekByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s);//根据日历的规则，给当前日期减往星期几与一个星期第一天的差值 
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }


    /**
     * 获取当前日期上一周的结束日期 （周六）
     */
    public static String previousWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }


    /**
     * 获取当前日期当前一周的开始日期 （周日）
     */
    public static String getCurrentWeekFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - s);//根据日历的规则，给当前日期减往星期几与一个星期第一天的差值 

        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 获取当前日期当前一周的结束日期 （周六）
     */
    public static String getCurrentWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int s = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() + (6 - s));

        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }


    /**
     * 返回上一个月的第一天
     *
     * @return 20120201
     */
    public static String previousMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 2, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 返回下一个月的第一天
     *
     * @return 20120401
     */
    public static String nextMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    /**
     * 返回当前月的第一天
     *
     * @return 20120101
     */
    public static String getCurrentMonthFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

}
