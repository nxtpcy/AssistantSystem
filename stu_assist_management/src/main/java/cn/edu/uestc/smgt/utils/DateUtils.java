package cn.edu.uestc.smgt.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
	// 原来的month判断不对[0-9][0-9]，应修改为0[1-9]|1[0-2]
	static final Pattern MONTH_PATTERN = Pattern.compile("0[1-9]|1[0-2]");
	static final Pattern YEAR_PATTERN = Pattern.compile("[0-9]+");
	static final Pattern DATE_PATTERN = Pattern.compile("[0-9]{6}");

	private DateUtils() {

	}

	/*
	 * 验证日期符合归法 XXXXXX(X为数字0-9)
	 */
	public static boolean dateValid(String date) {
		Matcher isValid = DATE_PATTERN.matcher(date);
		if (!isValid.matches()) {
			return false;
		}
		isValid = MONTH_PATTERN.matcher(date.substring(4));
		if (!isValid.matches()) {
			return false;
		}
		return true;
	}

	public static int monthString2Int(String month) {
		Matcher isNum = MONTH_PATTERN.matcher(month);
		if (!isNum.matches()) {
			throw new NumberFormatException("月份格式不正确");
		}
		String tempMonth = month;
		if (month.startsWith("0") && month.length() == 2) {
			tempMonth = month.substring(1);
		}
		return Integer.parseInt(tempMonth);
	}

	public static int yearString2Int(String year) {
		Matcher isNum = YEAR_PATTERN.matcher(year);
		if (!isNum.matches()) {
			throw new NumberFormatException("年份格式不正确");
		}
		return Integer.parseInt(year);
	}

	/**
	 * 获取年份：如201609中的2016
	 * 
	 * @param string
	 * @return
	 */
	public static String getYear(String string) {
		if (dateValid(string)) {
			return string.substring(0, 4);
		}
		return null;
	}

	/**
	 * 获取月份：如201609中的2016
	 * 
	 * @param string
	 * @return
	 */
	public static String getMonth(String string) {
		if (dateValid(string)) {
			String month = string.substring(4);
			if (month.charAt(0) == '0') {
				return month.substring(1);
			} else {
				return month;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param string
	 *            自1970年起的long型数字串
	 * @return
	 */
	public static Date long2Date(Long time) {
		if (time != null) {
			return new Date(time);
		}
		return null;
	}

	/**
	 * 
	 * @param args
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		return sdf.format(date);
	}

	public static String date2LongString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSSS");
		return sdf.format(date);
	}
	
	public static String date2LongStringForFileName(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		return sdf.format(date);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.monthString2Int("08"));
		System.out.println(DateUtils.monthString2Int("12"));
		System.out.println(DateUtils.monthString2Int("11"));
		System.out.println(DateUtils.yearString2Int("1991"));
		// System.out.println(DateUtils.yearString2Int("1a"));
		System.out.println(DateUtils.dateValid("199102"));
		System.out.println(DateUtils.dateValid("19912"));
		String year = "19912".substring(0, 4);
		String month = "199102".substring(4);
		System.out.println(year + " " + month);
		System.out.println(getYear("201609"));
		System.out.println(getMonth("201612"));
		System.out.println(date2String(new Date()));
	}
}
