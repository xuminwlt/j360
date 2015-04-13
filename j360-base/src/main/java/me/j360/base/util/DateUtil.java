package me.j360.base.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateUtil {
	public static void main(String[] args){
		Date date = new Date();
		System.out.println(year(date) + " " + month(date) + " " + day(date) );
		System.out.println(Integer.parseInt(year(date)));
		int year = 2014;
		Date beginDate = DateUtil.getFirstDateOfYear(String.valueOf(year));
		System.out.println(beginDate);
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static Date getFirstDateOfYear(String year){
		Calendar calendar = Calendar.getInstance();
		if(StringUtils.isEmpty(year)){
			calendar.set(calendar.get(calendar.YEAR), 0, 1, 0, 0, 0);
		}else{
			calendar.set(Integer.valueOf(year), 0, 1, 0, 0, 0);
		}
		return calendar.getTime();
	}

	public static Date getFirstDateOfMonth(Date date){
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), 1, 0, 0, 0);
			return calendar.getTime();
		}
	}

	/**
	 * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
	 *
	 * @param date 日期对象
	 * @param pattern 转换格式 例：yyyy-MM-dd
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}
	public static String year(Date date) {
		return DateToString(date,"yyyy");
	}
	public static String month(Date date) {
		return DateToString(date,"MM");
	}
	public static String day(Date date) {
		return DateToString(date,"dd");
	}
	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd格式转换成字符串返回
	 *
	 * @param date 日期对象
	 * @return String
	 */
	public static String DateToString(Date date) {
		String _pattern = "yyyy-MM-dd";
		return date == null ? null : DateToString(date, _pattern);
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm:ss格式转换成字符串返回
	 *
	 * @param date 日期对象
	 * @return String
	 */
	public static String DateTimeToString(Date date) {
		String _pattern = "yyyy-MM-dd HH:mm:ss";
		return date == null ? null : DateToString(date, _pattern);
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd HH:mm格式转换成字符串返回
	 *
	 * @param date 日期对象
	 * @return String
	 */
	public static String DateMinToString(Date date) {
		String _pattern = "yyyy-MM-dd HH:mm";
		return date == null ? null : DateToString(date, _pattern);
	}

	/**
	 * 功能: 将插入的字符串按格式转换成对应的日期对象
	 *
	 * @param str 字符串
	 * @param pattern 格式
	 * @return Date
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (str != null && !str.equals("")) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
		}
		return dateTime;
	}

	/**
	 * 功能: 将传入的字符串按yyyy-MM-dd格式转换成对应的日期对象
	 *
	 * @param str 需要转换的字符串
	 * @return Date 返回值
	 */
	public static Date StringToDate(String str) {
		String _pattern = "yyyy-MM-dd";
		return StringToDate(str, _pattern);
	}

	/**
	 * 功能: 将传入的字符串按yyyy-MM-dd HH:mm:ss格式转换成对应的日期对象
	 *
	 * @param str 需要转换的字符串
	 * @return Date
	 */
	public static Date StringToDateTime(String str) {
		String _pattern = "yyyy-MM-dd HH:mm:ss";
		return StringToDate(str, _pattern);
	}

	/**
	 * 功能: 将传入的字符串转换成对应的Timestamp对象
	 *
	 * @param str 待转换的字符串
	 * @return Timestamp 转换之后的对象
	 * @throws Exception
	 *             Timestamp
	 */
	public static Timestamp StringToDateHMS(String str) throws Exception {
		Timestamp time = null;
		time = Timestamp.valueOf(str);
		return time;
	}

	/**
	 * 功能: 根据传入的年月日返回相应的日期对象
	 *
	 * @param year 年份
	 * @param month 月份
	 * @param day 天
	 * @return Date 日期对象
	 */
	public static Date YmdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	/**
	 * 功能: 将日期对象按照MM/dd HH:mm:ss的格式进行转换，返回转换后的字符串
	 *
	 * @param date 日期对象
	 * @return String 返回值
	 */
	public static String communityDateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("MM/dd HH:mm:ss");
		String strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMaximum(11));
			calendar.set(12, calendar.getActualMaximum(12));
			calendar.set(13, calendar.getActualMaximum(13));
			calendar.set(14, calendar.getActualMaximum(14));
			return calendar.getTime();
		}
	}

	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(11, calendar.getActualMinimum(11));
			calendar.set(12, calendar.getActualMinimum(12));
			calendar.set(13, calendar.getActualMinimum(13));
			calendar.set(14, calendar.getActualMinimum(14));
			return calendar.getTime();
		}
	}
	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 *
	 * @param date 日期对象
	 * @param afterYears 往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterYears(Date date, int afterYears) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, afterYears);
		return cal.getTime();
	}
	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 *
	 * @param date 日期对象
	 * @param afterMonths 往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterMonths(Date date, int afterMonths) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, afterMonths);
		return cal.getTime();
	}
	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 *
	 * @param date 日期对象
	 * @param afterDays 往后天数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterDay(Date date, int afterDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, afterDays);
		return cal.getTime();
	}
	/**
	 * 功能：返回传入日期对象（date）之后afterDays天数的日期对象
	 *
	 * @param date 日期对象
	 * @param afterMinutes 往后天数正数，否则负数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterMinutes(Date date, int afterMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, afterMinutes);
		return cal.getTime();
	}
	/**
	 * 功能：返回传入日期对象（date）之后afterTimes时间的日期对象
	 * @param date 日期对象
	 * @param afterTimes 往后时间正数，否则负数
	 * @return java.util.Date 返回值
	 */
	public static Date getAfterTimes(Date date, long afterTimes) {
		return new Date(date.getTime()+afterTimes);
	}
	// day
	/**
	 * 功能: 返回date1与date2相差的天数
	 *
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int DateDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000);
		return i;
	}
	public static int YearDiff(Date date1, Date date2) {
		int i = (int) ((date1.getTime() - date2.getTime()) / 3600 / 24 / 1000 /365);
		return i;
	}

	// min
	/**
	 * 功能: 返回date1与date2相差的分钟数
	 *
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static long MinDiff(Date date1, Date date2) {
		long i = (long) ((date1.getTime() - date2.getTime()) / 1000 / 60);
		return i;
	}

	// second
	/**
	 * 功能: 返回date1与date2相差的毫秒数
	 *
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static long TimeDiff(Date date1, Date date2) {
		long i = (long) ((date1.getTime() - date2.getTime()));
		return i;
	}

	/**
	 * 以友好的方式显示时间
	 * @param time
	 * @return
	 */
	public static String friendly_time(Date time) {
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		//判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
			return ftime;
		}

		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"分钟前";
			else
				ftime = hour+"小时前";
		}
		else if(days == 1){
			ftime = "昨天";
		}
		else if(days == 2){
			ftime = "前天";
		}
		else if(days > 2 && days <= 10){
			ftime = days+"天前";
		}
		else if(days > 10){
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 返回日期之间的日期列表
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Set<String> getBewteenDates(Date date1, Date date2) {
		Set<String> dates = new HashSet<String>();
		if(date1!=null){
			if(date2!=null){
				Date tempDate = StringToDate(DateToString(date1));
				while(tempDate.getTime()<=date2.getTime()){
					dates.add(DateToString(tempDate));
					tempDate = getAfterDay(tempDate, 1);
				}
			}else{
				dates.add(DateToString(date1));
			}
		}
		return dates;
	}

	/**
	 * 返回日期之间的日期列表
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Set<String> getBewteenDates(Date date1, Date date2, Date begin, Date end) {
		Set<String> dates = new HashSet<String>();
		if(date1!=null){
			if(date2!=null){
				Date tempDate = StringToDate(DateToString(date1));
				while(tempDate.getTime()<=date2.getTime()){
					if(tempDate.getTime()>=begin.getTime()&&tempDate.getTime()<end.getTime()){
						dates.add(DateToString(tempDate));
					}
					tempDate = getAfterDay(tempDate, 1);
				}
			}else{
				dates.add(DateToString(date1));
			}
		}
		return dates;
	}

	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}
}
