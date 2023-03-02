package org.sky.framework.api.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author yangcong
 *
 * @date 2022/10/18
 */
public class DateUtil {
    public static final long _HOUR = 3600 * 1000L;
    public static final long _7_DAY_MILLION = 7L * 24 * 3600 * 1000;
    public static final long _8_HOUR = 8 * 3600 * 1000L;
    public static final long _24_HOUR = 3 * _8_HOUR;
    public static final long _30_DAY = 30 * _24_HOUR;

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String HOUR_FORMAT = "HH:mm:ss";
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    
    public static SimpleDateFormat sdf2 = new SimpleDateFormat(DAY_FORMAT);

    public static boolean isDate(String date, String format) {
        try {
            return null != new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            return false;
        }
    }

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
                    format.setLenient(false);
                    return format;
                }
            };
    private static final ThreadLocal<SimpleDateFormat> DATE_PATH_FORMATTER =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    format.setLenient(false);
                    return format;
                }
            };

    public static String getDateDirPath(long timeStamp) {
        return DATE_PATH_FORMATTER.get().format(timeStamp);
    }

    /**
     * 日期目录格式
     *
     * @param timeStamp
     * @return
     */
    public static String getDateDirName(long timeStamp) {
        return DATE_FORMATTER.get().format(timeStamp);
    }

    /**
     * 获取时间戳，指定月、日、时、分、秒（毫秒默认为0）
     *
     * @param month
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static long get(int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), month - 1, date, hour, minute, second);

        return (calendar.getTimeInMillis() / 1000) * 1000;
    }

    public static String toMdHm(long millis) {
        return new SimpleDateFormat("M月d日H时m分").format(new Date(millis));
    }

    public static Date parse(String str, String pattern) throws ParseException {
        return parseDate(str, pattern);
    }

    public static Long parseTime(String str, String pattern) {
        Date date = parseDate(str, pattern);
        if (null == date) {
            return null;
        } else {
            return date.getTime();
        }
    }

    public static Date parseDate(String date,String format){
        if(StringUtils.isEmpty(date)){
        	return null;
        }
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		 try {
			return sdf.parse(date);
		} catch (ParseException e) {
		    return null;
		} 
	} 

    /*
     * yyyy-MM
     */
    private static final ThreadLocal<SimpleDateFormat> dateFormatterMonth =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    format.setLenient(false);
                    return format;
                }
            };

    /*
     * yyyy-MM-dd
     */
    private static final ThreadLocal<SimpleDateFormat> dateFormatter =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    format.setLenient(false);
                    return format;
                }
            };

    /*
     * yyyy-MM-dd HH:mm:ss
     */
    private static final ThreadLocal<SimpleDateFormat> dateFormatterMinute =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT);
                    format.setLenient(false);
                    return format;
                }
            };

    /**
     * <tt>yyyy-MM-dd</tt>形式表达的时间转换为 Date 返回
     *
     * @param dateString
     * @return
     * @throws IllegalArgumentException
     */
    public static Date parseDate(String dateString) throws IllegalArgumentException {
        try {
            return dateFormatter.get().parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不正确，正确格式为：yyyy-MM-dd", e);
        }
    }

    public static Date parseTime(String dateString) throws IllegalArgumentException {
        try {
            return dateFormatterMinute.get().parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式不正确，正确格式为：yyyy-MM-dd HH:mm:ss", e);
        }
    }


    public static String getDateStr(long time) throws IllegalArgumentException {
        return dateFormatter.get().format(new Date(time));
    }

    /**
     * yyyy-MM
     * @param time
     * @return
     * @throws IllegalArgumentException
     */
    public static String getDateMonthStr(long time) throws IllegalArgumentException {
        return dateFormatterMonth.get().format(new Date(time));
    }

    /**
     * 格式化，默认格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp
     * @return
     */
    public static final String format(Long timestamp) {
        return format(timestamp, FORMAT_DEFAULT);
    }

    public static final String format(Date date) {
        if (null == date) {
            return "";
        }
        return format(date, FORMAT_DEFAULT);
    }

    public static final String format(Long timestamp, String pattern) {
        if (null == timestamp || 0 == timestamp) {
            return "";
        }
        return format(new Date(timestamp), pattern);
    }

    public static final String format(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 将yyyyMM格式的时间转换为目标格式的时间
     * @param source
     * @param destPattern
     * @return
     * @throws Exception
     */
    public static final String format(String source, String destPattern) throws Exception {
        return format(new SimpleDateFormat("yyyyMM").parse(source), destPattern);
    }

    public static final String To_年月日时分秒(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("MM月dd日HH时mm分ss秒").format(date);
    }

    public static final String To_年月日时分秒(long time) {
        return To_年月日时分秒(new Date(time));
    }

    public static final String To_年月日(Date date) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat("yyyy年MM月dd日").format(date);
    }

    public static final String To_年月日(long time) {
        return To_年月日(new Date(time));
    }

    public static Date getLastWeekAgo() {
        return getWeeksAgoTime(new Date(), 1);
    }

    public static Date getWeeksAgoTime(Date date, long weeks) {
        if (weeks == 0) {
            return date;
        }
        return new Date(date.getTime() - (weeks * _7_DAY_MILLION));
    }

    /**
     * 获取N周前的零点时间戳
     *
     * @param weeks
     * @return
     */
    public static long getTSWeeksAgo(int weeks) {
        long ts = System.currentTimeMillis() - weeks * _7_DAY_MILLION;
        return ts - ts % _24_HOUR - _8_HOUR;
    }

    /**
     * 获取N天前的零点时间戳
     *
     * @param days
     * @return
     */
    public static long getTSDaysAgo(int days) {
        // long ts = System.currentTimeMillis() - days * _24_HOUR;
        // return ts - ts % _24_HOUR - _8_HOUR;
        return getStartTimestampOfDate(System.currentTimeMillis() - days * _24_HOUR);
    }

    /**
     * 获取传入时间当天的起始时间
     */
    @SuppressWarnings("deprecation")
    public static Date getStartTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900 + date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取传入时间当天的结束时间
     */
    @SuppressWarnings("deprecation")
    public static Date getEndTimeOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900 + date.getYear(), date.getMonth(), date.getDate(), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static long getEndTimestampOfDate(Date date) {
        return getEndTimeOfDate(date).getTime();
    }

    public static long getEndTimestampOfDate(long timestamp) {
        return getEndTimeOfDate(new Date(timestamp)).getTime();
    }
    
    /**
     * 获取N分钟以前的时间
     */
    public static Date getNMinutesAgo(int minutes) {
        return new Date(System.currentTimeMillis() - (minutes * 60L * 1000));
    }

    /**
     * 获取N小时以前的时间
     */
    public static Date getNHoursAgo(int hours) {
        return new Date(System.currentTimeMillis() - (hours * 60L * 60 * 1000));
    }

    public static Date getNDaysLater(int n) {
        return getNDaysAgo(-n);
    }

    public static Date getNDaysAgo(int day) {
        return new Date(System.currentTimeMillis() - (day * 24L * 3600 * 1000));
    }
    
    /**
     * 获得指定日期后几天的日期
     * 
     * @param fromDate
     * @param day
     * @return
     */
    public static Date getDateAfterDays(Date fromDate, int day) {
    	Calendar calendar = new GregorianCalendar(); 
    	calendar.setTime(fromDate); 
    	calendar.add(Calendar.DATE, day);
    	
    	return calendar.getTime();  
    }
    
    public static Date getNHoursDiff(Date date, int hours){
    	return new Date(date.getTime() - (hours * 60L * 60 * 1000));
    }
    
    public static Date getNMinutesDiff(Date date, int minutes){
    	return new Date(date.getTime() - (minutes * 60L * 1000));
    }

    @SuppressWarnings("deprecation")
    public static long getStartTimestampOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900 + date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
        return calendar.getTimeInMillis() / 1000 * 1000;
    }

    public static long getStartTimestampOfDate(long timestamp) {
        return getStartTimestampOfDate(new Date(timestamp));
    }

    public static String toyyyy_MM_dd_HH_mm_ss(Date date) {
        return new SimpleDateFormat(FORMAT_DEFAULT).format(date);
    }

    public static long getTimestampNDaysAgo(int n) {
        return System.currentTimeMillis() - n * _24_HOUR;
    }

    /**
     * 获取 年+月 的整数值
     *
     * @return
     */
    public static int getYYYYMM() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取上个有的 年+月 的整数值
     *
     * @return
     */
    public static int getYYYYMMofLastMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = month == 0 ? calendar.get(Calendar.YEAR) - 1 : calendar.get(Calendar.YEAR);
        int lastMonth = month == 0 ? 12 : month;
        return year * 100 + lastMonth;
    }

    static long etime;
    static long day = 24 * 60 * 60 * 1000;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, 10, 11, 0, 0, 0);
        etime = calendar.getTime().getTime();
    }

    public static long get11Time() {
        long time = etime - System.currentTimeMillis();
        long days = time / day;
        if (days == 0 && time > 0) {
            return 1;
        }
        return days;
    }

    /**
     * 根据传入的时间得到当天日期，比如传入"2014-03-25 13:18:34"，今天日期为"2015-12-17",则得到的结果为"2015-12-17 13:18:34"
     */
    public static Date getDateByDate(Date date) {

        String day = format(System.currentTimeMillis(), DAY_FORMAT);
        String time = format(date, HOUR_FORMAT);

        time = day+" "+time;
        Long retTime = parseTime(time, FORMAT_DEFAULT);
        return new Date(retTime);
    }

    /**
     * 如果startDate 在endDate之后，则把endDate调整到后一天。
     * @return
     */
    public static Date getEndDateByStartDateAndEndDate(Date startDate, Date endDate) {
        if(startDate.after(endDate)){
            String date = dateFormatter.get().format(startDate);
            String time = format(endDate, HOUR_FORMAT);
            time = date+" "+time;
            long retTime = parseTime(time, FORMAT_DEFAULT);
            Calendar c = Calendar.getInstance();
            c.setTime(new Date(retTime));
            c.add(Calendar.DATE, 1);
            return c.getTime();
        }
        else {
            return endDate;
        }
    }

    public static Date getNYeasAgo(int year){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        return c.getTime();
    }

    public static Date getNextDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static Date getNMonthLater(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    public static int differMonthBetweenTwoDate(Date date1, Date date2){
        int diff = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        while (calendar.getTime().before(date2)){
            diff++;
            calendar.add(Calendar.MONTH, 1);
        }
        return diff;
    }

    //得到上个月的第一天
    public static Date getStartOfLastMonth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.MONTH, -1);

        return getStartTimeOfDate(c.getTime());
    }

    public static Date getNMonthAgo(int n){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -n);

        return c.getTime();
    }

    public static Date getNMonthLater(int n) {
        return getNMonthAgo(-n);
    }

    /**
     *
     * @param date1
     * @param date2
     * date2 - date1
     * @return
     */
    public static int daysBetween(Date date1,Date date2)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 得到上个月的最后一天
     * @return
     */
    public static Date getEndOfLastMonth(){
        Calendar c = Calendar.getInstance();
        //c.set(Calendar.MONTH,-1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.MONTH,-1);

        return getStartTimeOfDate(c.getTime());
    }

    /**
     * 得到当前月的开始时间
     * @return
     */
    public static long getFirstDayStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到当前月的结束时间
     * @return
     */
    public static long getLastDayEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTimeInMillis();

    }

    public static String formatChange(String timeStr, String formatFrom, String formatTo) {
        return formatChange(timeStr, formatFrom, formatTo, 0);
    }

    public static String formatChange(String timeStr, String formatFrom, String formatTo, long addTime) {
        Date date = null;
        try {
            date = new SimpleDateFormat(formatFrom).parse(timeStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException(timeStr + "无法转为" + formatFrom + "格式", e);
        }
        if (addTime != 0) {
            date = new Date(date.getTime() + addTime);
        }
        return new SimpleDateFormat(formatTo).format(date);
    }
    
    
    public static final String[] PARSE_PATTERNS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyyMMdd", "yyyyMMdd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss"};
    public static String getStartDayBefore(String dateStr, int days, String format) throws ParseException {
    	Date date = DateUtil.parse(dateStr, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR, days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return DateFormatUtils.format(calendar.getTime(), format);
    }
    
    //计算两日期相差天数
    public static int daysBetween(String dateStr1,String dateStr2, String format) throws ParseException {
    	Date date1 = stringDate(dateStr1, format);
    	Date date2 = stringDate(dateStr2, format);
    	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    } 
    
    
    public static Date stringDate(String time, String format) {
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
    		return sdf.parse(time);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    
    public static List<String> getDayList(String dateStart, int dayNum, String format) throws ParseException {
    	List<String> dayList = new ArrayList<String>();
    	for(int i=0; i<dayNum; i++){
    		dayList.add(getStartDayBefore(dateStart, i, format));
        }
		return dayList;
    }
    
    
    /**
     * LocalDateTime转为String
     * @param date
     * @param format
     * @return
     */
  	public static String formatLocalDateTimeString(LocalDateTime date, String format) {
  		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
  		return date.format(dtf);
  	}
  	
  	
  	/**
     * 秒差
     *
     * @param before
     * @param after
     * @return
     */
    public static long diffSecond(Date before, Date after) {
        return (after.getTime() - before.getTime()) / 1000;
    }

    /**
     * 分种差
     *
     * @param before
     * @param after
     * @return
     */
    public static int diffMinute(Date before, Date after) {
        return (int) (after.getTime() - before.getTime()) / 1000 / 60;
    }

    /**
     * 时差
     *
     * @param before
     * @param after
     * @return
     */
    public static int diffLocalHour(LocalDateTime before, LocalDateTime after) {
        return (int) (after.toInstant(ZoneOffset.of("+8")).toEpochMilli() - before.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / 1000 / 60 / 60;
    }
    
    /**
     * 分差
     *
     * @param before
     * @param after
     * @return
     */
    public static int diffLocalMinute(LocalDateTime before, LocalDateTime after) {
        return (int) (after.toInstant(ZoneOffset.of("+8")).toEpochMilli() - before.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / 1000 / 60;
    }
    
    /**
     * 秒差
     *
     * @param before
     * @param after
     * @return
     */
    public static int diffLocalSecond(LocalDateTime before, LocalDateTime after) {
        return (int) (after.toInstant(ZoneOffset.of("+8")).toEpochMilli() - before.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / 1000;
    }
    
    /**
     * 字符串转LocalDateTime
     * @param date
     * @param format
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String date, String format) {
    	DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
    	LocalDateTime ldt = LocalDateTime.parse(date, df);
		return ldt;
    }
    
    /**
     * 获取日期是星期几
     * @param date
     * @return
     */
    public static Integer getWeekOfDate(Date date) {
        Integer[] weekDays = {7, 1, 2, 3, 4, 5, 6 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    
    /**
     * 得到某天的第一秒
     * 
     * @return
     */
    public static Date getStartTime(Date date) {
        if (date == null || "".equals(date)) {
            return null;
        }
        String endTime = DateUtil.format(date, "yyyy-MM-dd");
        return DateUtil.parseDate(endTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 得到某天的最后一秒
     * 
     * @return
     */
    public static Date getEndTime(Date date) {
        if (date == null || "".equals(date)) {
            return null;
        }
        String endTime = DateUtil.format(date, "yyyy-MM-dd");
        return DateUtil.parseDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
    }

    
    /**
     * 判断传到PDD的筛选时间是否符合要求
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String returnError(String beginTime, String endTime){
    	String nowDate = format(new Date(), "yyyy-MM-dd");
    	String thirtyDaysAgo = format(getNDaysAgo(30), "yyyy-MM-dd");
    	try {
    		if(sdf2.parse(beginTime).getTime() < sdf2.parse(thirtyDaysAgo).getTime()) return "时间段中的起始时间不得早于30天前";
    		if(sdf2.parse(endTime).getTime() > sdf2.parse(nowDate).getTime()) return "时间段中的截止时间不得晚于今日";
    		
    		if(sdf2.parse(beginTime).getTime() != sdf2.parse(endTime).getTime()){
    			if(sdf2.parse(endTime).getTime() == sdf2.parse(nowDate).getTime()) return "时间段数据不包含今日数据";
    		}
        } catch (Exception e) {
            return "日期筛选有误，请重试";
        }
    	return "1";
    }
    
    
    /**
     * 获取当前时间的小时数,小时小于10的前面补0
     * @return
     */
    public static String returnHh(Date date){
    	Calendar now = Calendar.getInstance();
    	now.setTime(date);
    	String hStr = "";
    	int h = now.get(Calendar.HOUR_OF_DAY);
    	if(h<10){
    		hStr = "0"+h;
    	}else{
    		hStr = h+"";
    	}
		return hStr;
    }
    
    
    /**
     * 获取当前时间的分钟数,小时小于10的前面补0
     * @return
     */
    public static String returnMm(Date date){
    	Calendar now = Calendar.getInstance();
    	now.setTime(date);
    	String mStr = "";
    	int m = now.get(Calendar.MINUTE);
    	if(m<10){
    		mStr = "0"+m;
    	}else{
    		mStr = m+"";
    	}
		return mStr;
    }
    
    public static List<String> getLatelyTwentyFourHourDateList(){
    	List<String> list = new ArrayList<>();
    	for(int i=23;i>=0;i--){
    		String s = format(getNHoursAgo(i),"yyyy-MM-dd HH")+":00";
    		list.add(s);
    	}
    	return list;
    }
    
    
    //计算两日期相差小时数
    public static int hoursBetween(String dateStr1, String dateStr2, String format) throws ParseException {
    	Date date1 = stringDate(dateStr1, format);
    	Date date2 = stringDate(dateStr2, format);
    	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_hours=(time2-time1)/(1000*3600);

        return Integer.parseInt(String.valueOf(between_hours));
    } 
    
    public static List<String> getLatelyTSomeHourDateList(int hour){
    	List<String> list = new ArrayList<>();
    	for(int i=hour;i>=0;i--){
    		String s = format(getNHoursAgo(i),"yyyy-MM-dd HH")+":00";
    		list.add(s);
    	}
    	return list;
    }
    
    //得到前多少天的日期集合，不包含今天
    public static List<String> getLatelySomeDayList(int day){
    	List<String> list = new ArrayList<>();
    	for(int i=day;i>0;i--){
    		String dayStr = format(getNDaysAgo(i), "yyyy-MM-dd");
    		list.add(dayStr);
    	}
    	return list;
    }
    
    /**
     * 获得指定开始日期和结束日期的时间区间
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<String> getDateSection(Date beginDate, Date endDate){
    	List<String> list = new ArrayList<>();
    	int diffDays = DateUtil.daysBetween(beginDate, endDate);
    	for(int i=0; i<diffDays; i++){
    		String dayStr = format(getDateAfterDays(beginDate, i), "yyyy-MM-dd");
    		list.add(dayStr);
    	}
    	
    	return list;
    }
    
    /**
     * 获得指定开始日期和结束日期的时间区间
     * 
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static List<String> getDateSection(String beginDateStr, String endDateStr){
    	List<String> list = new ArrayList<>();
    	Date beginDate = parseDate(beginDateStr, "yyyy-MM-dd");
    	Date endDate = parseDate(endDateStr, "yyyy-MM-dd");
    	
    	int diffDays = DateUtil.daysBetween(beginDate, endDate);
    	for(int i=0; i<=diffDays; i++){
    		String dayStr = format(getDateAfterDays(beginDate, i), "yyyy-MM-dd");
    		list.add(dayStr);
    	}
    	
    	return list;
    }
    
    
    
    public static List<String> getDateSection2(String beginDateStr, String endDateStr){
    	List<String> list = new ArrayList<>();
    	Date beginDate = parseDate(beginDateStr, "yyyy-MM-dd HH:mm:ss");
    	Date endDate = parseDate(endDateStr, "yyyy-MM-dd HH:mm:ss");
    	
    	int diffDays = DateUtil.daysBetween(beginDate, endDate);
    	for(int i=0; i<=diffDays; i++){
    		String dayStr = format(getDateAfterDays(beginDate, i), "yyyy-MM-dd HH:mm:ss");
    		list.add(dayStr);
    	}
    	
    	return list;
    }
    
    public static List<String> getDateTimeSection(String beginDateTimeStr, String endDateTimeStr, boolean isASC){
    	List<String> list = new ArrayList<>();
    	Date beginDate = parseDate(beginDateTimeStr, "yyyy-MM-dd HH:mm:ss");
    	Date endDate = parseDate(endDateTimeStr, "yyyy-MM-dd HH:mm:ss");
    	
    	int diffDays = DateUtil.daysBetween(beginDate, endDate);
    	if(isASC) {
    		for(int i=0; i<=diffDays; i++){
        		String dayStr = format(getDateAfterDays(beginDate, i), "yyyy-MM-dd HH:mm:ss");
        		list.add(dayStr);
        	}
    	}else {
    		for(int i=0; i<=diffDays; i++){
        		String dayStr = format(getDateAfterDays(endDate, i*-1), "yyyy-MM-dd HH:mm:ss");
        		list.add(dayStr);
        	}
    	}
    	
    	
    	return list;
    }
    
    
    public static String stampToDate(String s, String format){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    
    /**
     * 时间now在 时间start - end 时间段内
     * @param now
     * @param start
     * @param end
     * @return
     */
    public static Boolean withinOfTime(LocalDateTime now, LocalDateTime start, LocalDateTime end){
    	if(start==null || end==null) return false;
    	if((start.isBefore(now) || start.isEqual(now)) && (end.isAfter(now) || end.isEqual(now))){
    		return true;
    	}
    	return false;
    }
    
    
    /**
     * 日期 now在 日期start - end 日期段内
     * @param now
     * @param start
     * @param end
     * @return
     */
    public static Boolean withinOfDate(LocalDate now, LocalDate start, LocalDate end){
    	if(start==null || end==null) return false;
    	if((start.isBefore(now) || start.isEqual(now)) && (end.isAfter(now) || end.isEqual(now))){
    		return true;
    	}
    	return false;
    }
    
    
    
    /**
	 * 
	 * @param date
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(Date date,String format){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format); 
		 return sdf.format(date); 
	}
    
	/**
	 * 根据时间获取页面展示的短时间格式
	 * @param date
	 * @return
	 */
    public static String getShortTime(Date date) {
		if(date ==null){
			return "";
		}
		Calendar  calendar = Calendar.getInstance();
		calendar.setTime(date);
        String shortstring =null;
        
        long now = Calendar.getInstance().getTimeInMillis(); 
         
        long deltime = (now - date.getTime())/1000;
        if(deltime > 24*60*60) {
        	if(deltime < 360*24*60*60 || formatDate(date,"yyyy").equals(formatDate(new Date(now),"yyyy"))){
        		shortstring = DateUtil.formatDate(date, "MM-dd"); 
        	}else{
        		shortstring = DateUtil.formatDate(date, "yyyy-MM-dd"); 
        	} 
        }else if(deltime > 60*60) { 
            shortstring = (int)(deltime/(60*60)) +"小时前";  
//        	shortstring = DateUtil.formatDate(date, "HH:mm"); 
        }else if(deltime > 60) {
            shortstring = ((int)(deltime/(60))) +"分钟前";
        }/*else if(deltime > 1) {
            shortstring = deltime +"秒前";
        }*/else{
            shortstring ="刚刚";
        }
        return shortstring;
	 }


    public static int compareTo(Date date1, Date date2){
        if (date1.compareTo(date2) > 0) {
            return 1;
        } else if (date1.compareTo(date2) < 0) {
            return -1;
        } else if (date1.compareTo(date2) == 0) {
            return 0;
        }
        return 0;
    }


    
    /**
     * java.time.LocalDateTime 转为 java.util.Date
     * @param dateToConvert
     * @return
     */
    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    public static void main(String[] args) throws ParseException {
        System.out.println(getDateAfterDays(new Date(), 10));

    	Date date = parseDate("2022-04-15 23:59:00", "yyyy-MM-dd HH:mm:ss");
    	System.out.println(getShortTime(date));
    	
    	Date date1 = parseDate("2022-04-17 15:09:20", "yyyy-MM-dd HH:mm:ss");
    	System.out.println(getShortTime(date1));
    	
    	Date date2 = parseDate("2022-04-17 15:22:20", "yyyy-MM-dd HH:mm:ss");
    	System.out.println(getShortTime(date2));
    	
    	System.out.println(getShortTime(new Date()));
    	
    	String s = "供应描述不得少于11供应描述不得少于111";
    	if(s.length()<10) System.out.println("供应描述不得少于10字！");
		if(s.length()>20) System.out.println("供应描述不得大于500字！");
		
		Integer n = null;
		if(n>0){
			System.out.println("@@@@@@@@@");
		}
    	
//    	List<Integer> list = new ArrayList<Integer>();
//		for (int i = 0; i < 5; i++) {
//			list.add(i);
//		}
//		System.out.println("orginal List:");
//		for (Integer s : list) {
//			System.out.print(s);
//		}
//		System.out.println();
//		
//		Collections.shuffle(list);
//		System.out.println("after shuffle List:");
//		for (Integer s : list) {
//			System.out.print(s);
//		}
    	
    	
    	
//    	String s = returnError("2020-03-17", "2020-03-17");
//    	System.out.println(s);
    	
//    	Date date = parseDate("2020-03-31 23:59:00", "yyyy-MM-dd HH:mm:ss");
//    	Calendar now = Calendar.getInstance();
//    	now.setTime(date);
//    	System.out.println("年："+ now.get(Calendar.YEAR));
//    	System.out.println("月："+ (now.get(Calendar.MONTH) + 1));
//    	System.out.println("日："+ now.get(Calendar.DAY_OF_MONTH));
//    	System.out.println("时："+ now.get(Calendar.HOUR_OF_DAY));
//    	System.out.println("分："+ now.get(Calendar.MINUTE));
//    	System.out.println("秒："+ now.get(Calendar.SECOND));
//    	
//    	System.out.println(returnHh(date));
//    	System.out.println(returnMm(date));
//    	
//    	System.out.println(format(getNHoursAgo(23),"yyyy-MM-dd HH"));
//    	System.out.println(format(getNHoursAgo(0),"yyyy-MM-dd HH:mm:ss"));
    	
//    	getLatelyTwentyFourHourDateList();
//    	System.out.println("yyyy-MM-dd HH:mm:ss".substring(0, "yyyy-MM-dd HH:mm:ss".length()-3));
//    	String d1 = DateUtil.format(DateUtil.getStartTime(new Date()),"yyyy-MM-dd HH:mm");
//    	System.out.println(d1);
//    	String d2 = DateUtil.format(DateUtil.getNHoursAgo(0),"yyyy-MM-dd HH");
//    	System.out.println(d2);
//    	
//    	System.out.println(hoursBetween(d1, d2, "yyyy-MM-dd HH"));
//    	System.out.println(getLatelyTSomeHourDateList(hoursBetween(d1, d2, "yyyy-MM-dd HH")));
//    	int day = 30;
//    	System.out.println(getLatelySomeDayList(day));
    	
//    	Date now = new Date();
//    	System.out.println(now);
//    	System.out.println(now.getTime()/1000);
//    	
//    	Date ye = getNHoursAgo(24);
//    	System.out.println(ye);
//    	System.out.println(ye.getTime()/1000);
    	
//    	List<String> list = getLatelySomeDayList(30);
//    	for(String day:list){
//    		System.out.println(day);
//    	}
    	
//    	String dayStr = format(getNDaysAgo(0), "yyyy-MM-dd");
//    	System.out.println(dayStr);
    	
//    	System.out.println(getDateAfterDays(new Date(), 5));
//    	Date d = new Date();
//    	System.out.println(format(d, "yyyy-MM-dd"));
    	
//    	System.out.println(getDateSection("2020-05-20","2020-05-24"));
    	
    }
}
