package test.ygy;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类,解决java DateFormat 线程安全问题、兼顾性能问题
 * 创建sdf次数 = 线程数*sdf类型个数
 */

public class ConcurrentDateUtil {

    /** 锁对象 */
    private static final Object lockObj = new Object();

    /** 存放不同的日期模板格式的sdf的Map */
    private static final Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap();

    /** 常用日期格式 **/
    /**  yyyy-MM-dd HH:mm:ss **/
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public final static String FORMAT_YYYYMMDD = "yyyyMMdd";

    public final static String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**  yyyy-MM-dd HH:mm:ss **/
    public final static String DATE_FORMAT = "yyyy-MM-dd";

    /** MM-dd **/
    public final static String SHORT_DATE_FORMAT ="MM-dd";
    /** MM月dd日 **/
    public final static String SHORT_DATE_CN_FORMAT ="MM月dd日";

    /** HH:mm **/
    public final static String SHORT_TIME_FORMAT ="HH:mm";
    /** HH分mm秒 **/
    public final static String SHORT_TIME_CN_FORMAT ="HH分mm秒";


    /**
     * 返回一个ThreadLocal的sdf,同一个pattern 每个线程只会new一次sdf
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (tl == null) {
            synchronized (lockObj) {
                tl = sdfMap.get(pattern);
                if (tl == null) {
                    // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     * 解决java 原生DateFormat 线程安全问题
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if(date==null || isEmpty(pattern)){
            return "";
        }

        try {
            return getSdf(pattern).format(date);
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param timeMillion
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String format(long timeMillion) {
        return format(new Date(timeMillion), DATE_TIME_FORMAT);
    }

    /**
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parse(String dateStr, String pattern) {
        if(isEmpty(dateStr) || isEmpty(pattern)){
            return null;
        }

        try {
            return getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param dateStr
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DATE_TIME_FORMAT);
    }


    private static int getCurrent(int calendarField){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentTimeStamp());
        return cal.get(calendarField); //Calendar.HOUR
    }

    public static int getCurrentHour(){
        return getCurrent(Calendar.HOUR_OF_DAY);
    }

    public static long diffDateStr(String begin, String end,String pattern, Type type) {
        Date beginDate=parse(begin, pattern);
        Date endDate=parse(end, pattern);
        return diffDate(beginDate, endDate, type);
    }

    /**
     * 取两个日期时间差
     *
     * @param begin
     * @param end
     * @param type 参考 Type
     * @return
     */
    public static long diffDate(Date begin, Date end, Type type) {
        long between = end.getTime() - begin.getTime();
        long s = (between / 1000);
        if (type == Type.DAY) {//天
            return between / (24 * 60 * 60 * 1000);
        } else if (type == Type.HOUR) {//小时
            return (between / (60 * 60 * 1000));
        } else if (type == Type.MINUTE) {//分钟
            return (between / (60 * 1000));
        } else if (type == Type.SECOND) {//秒
            return s;
        }
        return between;
    }

    /**
     * 日期加减天数
     * @param theDay
     * @param diff: <0 为向前天数, >0 向后天数
     * @return
     */
    public static String addDate(Date theDay,int diff){
        if(theDay==null){
            return "";
        }
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(theDay);
        calDate.add(Calendar.DAY_OF_MONTH,diff);
        return format(calDate.getTime(), DATE_FORMAT);
    }

    /**
     * 日期加减天数
     * @param theDay
     * @param diff: <0 为向前天数, >0 向后天数
     * @return
     */
    public static String addDate(String theDay,int diff){
        return addDate(parse(theDay,DATE_FORMAT),diff);
    }

    /**
     * 格式化显示时间
     *
     * @param date
     * @return
     * 1小时内:刚刚
     * 1<x≤24:小时前
     * ≥24:MM月dd日
     */
    public static String timeTips(Date date) {
        //取服务端当前时间
        Date now = new Date(getCurrentTimeStamp());
        long diff = diffDate(date, now, Type.HOUR);
        String result = "";
        if (diff < 1) {
            result = "刚刚";
        } else if (diff >= 1 && diff < 24) {
            //final int hour = (int)(diff*1.0f/60);
            result = String.valueOf(diff).concat("小时前");
        } else {
            result = format(date, SHORT_DATE_CN_FORMAT);
        }
        return result;
    }




    /**
     * 获取服务端纠正时间
     * @return
     */
    public static long getCurrentTimeStamp(){
        return -1;
        //return TimeStampManager.instance().getCurrentTimeStamp(); // 手淘取服务端时间
    }

    /**
     * 判断date是否今天,取云端北京时间比较
     *
     * @param date
     * @return 年月日都相同返回true,否则返回false
     */
    /*
    public static boolean isSameDay(Date date) {
        Date now = new Date(getCurrentTimeStamp());
        return isSameDay(date, now);
    }
    */

    /**
     * 判断两日期是否同一天
     *
     * @param dateA
     * @param dateB
     * @return 年月日都相同返回true,否则返回false
     */
    public static boolean isSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param tailDate 后一个时间
     * @param preDate  前一个时间
     * @return
     */
    public static String buildTimeDistance(Date tailDate, Date preDate, String defaultString) {
        long diffTime = tailDate.getTime() - preDate.getTime();
        if (diffTime > 0) {
            int seconds = (int) (diffTime / 1000);
            if (seconds <= 0) {
                return "";
            }
            if (seconds < 60) {
                return seconds + "秒前";
            }

            int minutes = (int) (diffTime / 1000 / 60);
            if (minutes <= 0) {
                return "";
            }

            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    return format(preDate, SHORT_DATE_FORMAT);
                } else {
                    return hours + "小时前";
                }

            } else {
                return minutes + "分钟前";
            }
        }
        return defaultString;
    }

    private static boolean isEmpty(String str){
        return (str==null || str.trim().isEmpty());
    }

    /**
     * 时间差类型
     */
    public enum Type{
        DAY, HOUR, MINUTE, SECOND,MILL
    }

    /**
     * 按分钟偏移,根据{@code source}得到{@code second}秒之后的日期<Br>
     *
     * @param source , 要求非空
     * @param second , 秒数,可以为负
     * @return 新创建的Date对象
     * @author xueye.duanxy
     */
    public static Date addSeconds(Date source, int second) {
        return addDate(source, Calendar.SECOND, second);
    }


    /**
     * Add Date并且返回一个新的日历对象
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    public static Date addDate(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static void main(String[] args) {

        System.out.println(getLastWeekFirst());
        System.out.println(getLastWeekEnd());
        System.out.println(getLastMonthFirst());
        System.out.println(getLastMonthEnd());
        System.out.println(getMonthInYear());
        System.out.println(getYear());
        System.out.println(getQuarterInYear());
        System.out.println(getDaysInYear());
        System.out.println(getLastQuarterEnd());
        System.out.println(getLastQuarterFirst());
    }

    /**
     * 获取当前年的自然季度数
     * @return
     */
    public static int getQuarterInYear() {
        int monthInYear=getMonthInYear();
            if (monthInYear >= 0 && monthInYear <3) // 1-3月;0,1,2
                return 1;
            else if (monthInYear >2 && monthInYear <6) // 4-6月;3,4,5
                return 2;
            else if (monthInYear >5 && monthInYear <9) // 7-9月;6,7,8
                return 3;
            else
                return 4;
    }

    /**
     * 获取当前年的
     * @return
     */
    public static int getDaysInYear() {
        Calendar calendar = Calendar.getInstance();
        int days =calendar.get(Calendar.DAY_OF_YEAR);
        return days;

    }

    /**
     * 获取当年第一天星期几
     * @return
     */
    public static int getFirstDayOfWeekInYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }



    /**
     * 获取当前年的自然月数
     * @return
     */
    public static int getMonthInYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH)+ 1;  //java内部从0开始
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取不含小时的date
     * @return
     */
    public static Date getCurrentDateWithNoHour() {
        return getCalendayWithNoHour().getTime();
    }

    /**
     * 获取上一个季度的最后一天
     * @return
     */
    public static String getLastQuarterEnd( ) {
        int quarterInYear = getQuarterInYear();
        int lastQuarterInyear = quarterInYear - 1;
        if (lastQuarterInyear == 0) {
            lastQuarterInyear = 4;
        }
        String time = null ;
        Calendar calendar = Calendar.getInstance();
        switch (lastQuarterInyear){
            case 1:
                calendar.set(Calendar.MONTH, 2);
                calendar.set(Calendar.DAY_OF_MONTH,31);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 2:
                calendar.set(Calendar.MONTH,5);
                calendar.set(Calendar.DAY_OF_MONTH,30);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 3:
                calendar.set(Calendar.MONTH,8);
                calendar.set(Calendar.DAY_OF_MONTH,30);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 4:
                calendar.set(Calendar.MONTH,11);
                calendar.set(Calendar.DAY_OF_MONTH,31);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
        }
        return time;
    }

    /**
     * 获取上一个季度的第一天
     * @return
     */
    public static String getLastQuarterFirst( ) {
        int quarterInYear = getQuarterInYear();
        int lastQuarterInyear = quarterInYear - 1;
        if (lastQuarterInyear == 0) {
            lastQuarterInyear = 4;
        }
        String time = null ;
        Calendar calendar = Calendar.getInstance();
        switch (lastQuarterInyear){
            case 1:
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH,1);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 2:
                calendar.set(Calendar.MONTH,3);
                calendar.set(Calendar.DAY_OF_MONTH,1);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 3:
                calendar.set(Calendar.MONTH,6);
                calendar.set(Calendar.DAY_OF_MONTH,1);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
            case 4:
                calendar.set(Calendar.MONTH,9);
                calendar.set(Calendar.DAY_OF_MONTH,1);
                time= format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
                break;
        }
        return time;
    }


    /**
     * 获取当前上一个月末
     * @return
     */
    public static String getLastMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE,-1);
        return format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
    }

    /**
     * 获取当前上一个月初
     * @return
     */
    public static String getLastMonthFirst() {
        Calendar calendar =getCalendayWithNoHour();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
    }


    /**
     * 获取当前上一周的星期一
     * @return
     */
    public static String getLastWeekFirst() {
        Calendar calendar =getCalendayWithNoHour();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset1 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        return format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
    }

    /**
     * 获取当前上一周的星期天
     * @return
     */
    public static String getLastWeekEnd() {
        Calendar calendar =getCalendayWithNoHour();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset2 = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
       return format(calendar.getTime(), ConcurrentDateUtil.FORMAT_YYYYMMDD);
    }



    public static Calendar getCalendayWithNoHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);              //把当前时间小时变成０
        calendar.set(Calendar.MINUTE, 0);            //把当前时间分钟变成０
        calendar.set(Calendar.SECOND, 0);            //把当前时间秒数变成０
        calendar.set(Calendar.MILLISECOND, 0);       //把当前时间毫秒变成０
        return calendar;
    }


    public static String getPeriodTime(String minutes) {
        // 新增二期时间格式 00'00'00''
        int time = Integer.valueOf(minutes);
        int hour = time / 60 ;
        StringBuilder stringBuilder=new StringBuilder();
        //拼接小时
        if (hour >= 10) {
            stringBuilder.append(hour).append(":");
        }else {
            stringBuilder.append(0).append(hour).append(":");
        }

        int min = time % 60 ;
        //拼接分钟
        if (min >= 10) {
            stringBuilder.append(min).append("\'");
        }else {
            stringBuilder.append(0).append(min).append("\'");
        }
        return stringBuilder.toString();
    }


}
