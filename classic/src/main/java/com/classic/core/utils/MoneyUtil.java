package com.classic.core.utils;

import java.math.BigDecimal;

/**
 * 高精度数据计算工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public class MoneyUtil {
	// 默认除法运算精度
	private static final int DEFAULT_SCALE = 2;

	private MoneyUtil() {
	}
	private BigDecimal root;

	public static MoneyUtil obtain(Object number){
		final MoneyUtil moneyUtil = new MoneyUtil();
		moneyUtil.root = objectToBigDecimal(number);
		return moneyUtil;
	}

	/** 减 */
	public MoneyUtil subtract(Object number){
		root = root.subtract(objectToBigDecimal(number));
		return this;
	}

	/** 乘 */
	public MoneyUtil multiply(Object number){
		root = root.multiply(objectToBigDecimal(number));
		return this;
	}

	/** 除 */
	public MoneyUtil divide(Object number){
		root = root.divide(objectToBigDecimal(number));
		return this;
	}

	/** 加 */
	public MoneyUtil add(Object number){
		root = root.add(objectToBigDecimal(number));
		return this;
	}

	/**
	 * 设置运算精度
	 * @param scale 精确到小数点后几位数
	 * @return
	 */
	public MoneyUtil setPrecision(int scale){
		root = root.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
		return this;
	}

	public BigDecimal create(){
		return root;
	}

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
			value = new BigDecimal(obj.toString());
		}else{//未知的类型
			throw new IllegalArgumentException("unknown type!");
		}
		return value;
	}

	/** 加 */
	public static BigDecimal add(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.add(b2);
	}

	/** 减 */
	public static BigDecimal subtract(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.subtract(b2);
	}

	/** 乘 */
	public static BigDecimal multiply(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.multiply(b2);
	}

	/** 除 */
	public static BigDecimal divide(Object v1, Object v2) {
		return divide(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * 除法
	 * @param v1
	 * @param v2
	 * @param scale 精确到小数点后几位数
	 * @return
	 */
	public static BigDecimal divide(Object v1, Object v2, int scale) {
		if (scale < 0) {//运算精度必须大于等于0
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 四舍五入
	 * @param v
	 * @param scale 精确到小数点后几位数
	 * @return
	 */
	public static BigDecimal round(Object v, int scale) {
		if (scale < 0) {//运算精度必须大于等于0
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = objectToBigDecimal(v);
		return b.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 去掉小数点后无效的0
	 * @param number
	 * @return
	 */
    public static String replace(Number number){
        String s = String.valueOf(number);
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }
}
