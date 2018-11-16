package com.tang.userserver.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil
{
	/**
	 * 将yyyy-MM-dd HH:mm:ss格式的字符串转成unix时间对应的long值
	 */
	public static long StrDate2LongDateMs(String date) 
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(date).getTime();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 将uninx格式的long值日期转换成yyyy-MM-dd HH:mm:ss格式的日期字符串
	 */
	public static String longDate2StrDate(long date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(date));
	}
	/**
	 * 获取某一天后面或前面的第几天
	 */
	public static long getYMDs(long now, int nDays)
	{
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date(now));//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, nDays);  //设置为后几天

		return calendar.getTime().getTime();
	}
	/**
	 * 获取某一天后面或前面的第几天
	 */
	public static String getYMDs(String now, int nDays)
	{
		try
		{
			Calendar calendar = Calendar.getInstance(); //得到日历
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(df.parse(now));//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, nDays);  //设置为后几天

			return df.format(calendar.getTime());
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}

		return "";
	}
	/**
	 * 将字符串日期格式转成long型的unix时间戳
	 */
	public static long strDate2LongDateSec(String date)
	{
		long ret = 0;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try
		{
			ret = df.parse(date).getTime()/1000;
		}
		catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 获取当前时间的字符串格式，年-月-日 时:分:秒的格式
	 */
	public static String getNowYMDHMS() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
	}

	/**
	 * 获取当前时间的字符串格式，年月日的格式
	 */
	public static String getNowYMD() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	/**
	 * 获取当前星期几
	 */
	public static String getWeek()
	{
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;

		if(week_index<0)
		{
			week_index = 0;
		}

		return weeks[week_index];
	}
	/**
	 * 获取当指定时间是星期几
	 */
	public static String getWeek(Date date)
	{
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;

		if(week_index<0)
		{
			week_index = 0;
		}

		return weeks[week_index];
	}
	/**
	 * 根据一个日期获取日期所在周的周日那天
	 */
	public static String getSunday()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        if(1 == dayWeek)
        {
          cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        System.out.println("所在周星期日的日期："+imptimeEnd);

        return sdf.format(cal.getTime());
	}
	/**
	 * 根据一个日期获取日期所在周的周日那天
	 */
	public static String getSunday(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        Calendar cal = Calendar.getInstance();

        try
        {
			cal.setTime(sdf.parse(date));
		}
        catch (ParseException e)
        {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天

        if(1 == dayWeek)
        {
          cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        System.out.println("所在周星期日的日期："+imptimeEnd);

        return sdf.format(cal.getTime());
	}
	/**
	 * 获取当前时间的字符串格式，年月加上第一天的格式，返回YYYYMM01
	 */
	public static String getNowYMFirstDay()
	{
		String now = new SimpleDateFormat("yyyyMM").format(new Date());
		now += "01";
		return now;
	}
	/**
	 * 获取前一天时间的字符串格式，年月日的格式
	 */
	public static String getPreYMD()
	{
		Date dNow = new Date();   //当前时间
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天

		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 获取当前年
	 */
	public static String getNowYear() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	/**
	 * 获取当前月
	 */
	public static String getNowMonth() {
		return new SimpleDateFormat("MM").format(new Date());
	}

	/**
	 * 计算两个日期之间相差的天数
	 */
	public static long daysBetweenDates(String beginDate, String endDate) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long between = 0;

		try {
			Date begin = dfs.parse(beginDate);
			Date end = dfs.parse(endDate);

			between = (end.getTime() - begin.getTime()) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return between;
	}

	/**
	 * 计算两个日期之间相差的月数
	 */
	public static int monthsBetweenDates(String beginDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int c = 0;

		try {
			Calendar cal1 = new GregorianCalendar();
			cal1.setTime(sdf.parse(beginDate));
			Calendar cal2 = new GregorianCalendar();
			cal2.setTime(sdf.parse(endDate));
			c = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12
					+ cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return c;
	}

	/**
	 * 几个月之前或之后
	 */
	public static String monthAfterDates(String date, int months) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String month = "";

		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(date));
			calendar.add(Calendar.MONTH, months);
			month = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return month;
	}

	/**
	 * 获取指定月份之前或之后几个月的日期 传正数，表示之前；传负数，表示之后
	 */
	public static String getDateAfterMonths(String givenDate, int months) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化对象
		Calendar calendar = Calendar.getInstance();// 日历对象

		try {
			// 判断是否传入日期
			if (null == givenDate || "".equals(givenDate)) {
				calendar.setTime(new Date());// 设置当前日期
			} else {
				calendar.setTime(sdf.parse(givenDate));// 设置当前日期
			}

			calendar.setTime(sdf.parse(givenDate));// 设置当前日期

			if (months == 0) {
				months = 1;
			}

			calendar.add(Calendar.MONTH, months * -1);// 月份减months
			System.out.println(sdf.format(calendar.getTime()));// 输出格式化的日期
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdf.format(calendar.getTime());
	}

	/**
	 * 取一个月的最后一天
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(1, year);

		cal.set(2, month - 1);

		cal.set(5, 1);
		int value = cal.getActualMaximum(5);
		cal.set(5, value);

		return new SimpleDateFormat("yyyy-MM-dd 23:59:59")
				.format(cal.getTime());
	}

	/**
	 * 取一个月的最后一天
	 */
	public static String getLastDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();

		cal.set(1, Integer.parseInt(date.substring(0, 4)));

		cal.set(2, Integer.parseInt(date.substring(5, 7)) - 1);

		cal.set(5, 1);
		int value = cal.getActualMaximum(5);
		cal.set(5, value);

		return new SimpleDateFormat("yyyy-MM-dd 23:59:59")
				.format(cal.getTime());
	}

	/**
	 * 方法描述:获取本月第一天
	 * 
	 * @throws ParseException
	 */
	public static String getFirstDay(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(df.parse(date));
		gcLast.set(5, 1);

		df = null;
		df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

		return df.format(gcLast.getTime());
	}

	/**
	 * 方法描述:获取一年的第一天
	 * 
	 * @throws ParseException
	 */
	public static String getFirstDayOfYear(String year) throws ParseException {
		StringBuffer sb = new StringBuffer();

		if (year.length() > 4) {
			sb.append(year.substring(0, 4));
		} else {
			sb.append(year);
		}

		sb.append("-01-01 00:00:00");

		return sb.toString();
	}

	/**
	 * 方法描述:获取下一年的第一天
	 * 
	 * @throws ParseException
	 */
	public static String getFirstDayOfNextXYear(String year, int next)
			throws ParseException {
		StringBuffer sb = new StringBuffer();

		if (year.length() > 4) {
			year = year.substring(0, 4);
		}

		sb.append(Integer.parseInt(year + next));
		sb.append("-01-01 00:00:00");

		return sb.toString();
	}

	/**
	 * 方法描述:获取一年的第一天
	 * 
	 * @throws ParseException
	 */
	public static String getLastDayOfYear(String year) throws ParseException {
		StringBuffer sb = new StringBuffer();

		if (year.length() > 4) {
			sb.append(year.substring(0, 4));
		} else {
			sb.append(year);
		}

		sb.append("-12-31 23:59:59");

		return sb.toString();
	}

	/**
	 * 方法描述:获取下一年的第一天
	 * 
	 * @throws ParseException
	 */
	public static String getLastDayOfNextXYear(String year, int next)
			throws ParseException {
		StringBuffer sb = new StringBuffer();

		if (year.length() > 4) {
			year = year.substring(0, 4);
		}

		sb.append(Integer.parseInt(year) + next);
		sb.append("-12-31 23:59:59");

		return sb.toString();
	}
	/**
	 * YYMMDDHHMMSS转成YYYY-MM-DD HH:MM:SS格式
	 */
	public static String converDate(String input)
	{
		if(null == input)
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("20");
		sb.append(input.substring(0, 2));
		sb.append("-");
		sb.append(input.substring(2, 4));
		sb.append("-");
		sb.append(input.substring(4, 6));
		sb.append(" ");
		sb.append(input.substring(6, 8));
		sb.append(":");
		sb.append(input.substring(8, 10));
		sb.append(":");
		sb.append(input.substring(10, 12));

		return sb.toString();
	}

	public static void main(String[] params) {
		System.out.println(daysBetweenDates("2015-12-16 12:24:35",
				"2015-12-14 12:23:35"));
		System.out.println(monthsBetweenDates("2014-12-16 12:24:35",
				"2015-12-14 12:23:35"));
		System.out.println(getDateAfterMonths("2008-01-05 12:24:3", -12));
		try {
			System.out.println(getFirstDay("2015-02-16 12:24:35"));
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println(DateUtil.getSunday("20160918"));
	}
}
