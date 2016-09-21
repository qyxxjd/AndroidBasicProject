package com.classic.core.utils;

import android.text.TextUtils;
import java.math.BigDecimal;

/**
 * 高精度数据计算工具类
 *
 * @author 续写经典
 * @version 1.0 2015/11/3
 */
public class MoneyUtil {
	// 默认除法运算精度
	private static final int DEFAULT_SCALE = 2;
	private static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	private MoneyUtil() { }

	private BigDecimal mBigDecimal;


	/** 对象转BigDecimal */
	public static BigDecimal objectToBigDecimal(Object obj){
		BigDecimal value = null;
		if(obj instanceof Integer){
			value = new BigDecimal(Integer.toString((Integer)obj));
		}else if(obj instanceof Float){
			value = new BigDecimal(Float.toString((Float)obj));
		}else if(obj instanceof Double){
			value = new BigDecimal(Double.toString((Double)obj));
		}else if(obj instanceof Short){
			value = new BigDecimal(Short.toString((Short)obj));
		}else if(obj instanceof Long){
			value = new BigDecimal(Long.toString((Long)obj));
		}else if(obj instanceof String){
			Integer.parseInt(obj.toString());
			value = new BigDecimal(obj.toString());
		}else{//未知的类型
			throw new IllegalArgumentException("unknown type!");
		}
		return value;
	}

	/**
	 * 去掉小数点后无效的0
	 * @param number
	 * @return
	 */
    public static String replace(Number number){
        return replace(String.valueOf(number));
    }

	/**
	 * 去掉小数点后无效的0
	 * @param number
	 * @return
	 */
	public static String replace(String number){
		if(TextUtils.isEmpty(number)) return "0";
		if(number.indexOf(".") > 0){
			number = number.replaceAll("0+?$", "");//去掉后面无用的零
			number = number.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		}
		return number;
	}

	public static MoneyUtil newInstance(Object number){
		MoneyUtil moneyUtil = new MoneyUtil();
		moneyUtil.mBigDecimal = objectToBigDecimal(number);
		return moneyUtil;
	}

	/** 减 */
	public MoneyUtil subtract(Object number){
		mBigDecimal = mBigDecimal.subtract(objectToBigDecimal(number));
		return this;
	}

	/** 乘 */
	public MoneyUtil multiply(Object number){
		mBigDecimal = mBigDecimal.multiply(objectToBigDecimal(number));
		return this;
	}

	/** 除 */
	public MoneyUtil divide(Object number){
		return divide(number, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
	}


	/**
	 * 除
	 * @param number
	 * @param scale 精确到小数点后几位数
	 * @return
	 */
	public MoneyUtil divide(Object number, int scale){
		return divide(number, scale, DEFAULT_ROUNDING_MODE);
	}


	/**
	 * 除
	 * @param number
	 * @param scale 精确到小数点后几位数
	 * @param roundingMode 精确模式
	 * @see java.math.BigDecimal
	 * @return
	 */
	public MoneyUtil divide(Object number, int scale, int roundingMode){
		mBigDecimal = mBigDecimal.divide(objectToBigDecimal(number), scale, roundingMode);
		return this;
	}

	/** 加 */
	public MoneyUtil add(Object number){
		mBigDecimal = mBigDecimal.add(objectToBigDecimal(number));
		return this;
	}

	/**
	 * 四舍五入
	 * @param scale 精确到小数点后几位数
	 * @return
	 */
	public MoneyUtil round(int scale) {
		return round(scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 四舍五入
	 * @param scale 精确到小数点后几位数
	 * @param roundingMode 精确模式
	 * @see java.math.BigDecimal
	 * @return
	 */
	public MoneyUtil round(int scale, int roundingMode) {
		if (scale >= 0) {
			mBigDecimal = mBigDecimal.divide(new BigDecimal("1"), scale, roundingMode);
		}
		return this;
	}

	public BigDecimal create(){
		return mBigDecimal;
	}
}
