package com.classic.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class MatcherUtil
{
	private MatcherUtil() { }

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$"); // 验证手机号  
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	
	/**
	 * email格式验证
	 */
	public static boolean isEmail(String email) {
		String emailPattern="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	    Pattern p = Pattern.compile(emailPattern);
	    Matcher m = p.matcher(email);
	    return m.matches();
	}
	
	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的  
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的  
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		}
		else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

}
