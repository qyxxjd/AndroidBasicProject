package com.classic.core.utils;

import java.math.BigDecimal;

/**
 * 金钱计算相关的工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class MoneyUtil {
	// 默认除法运算精度
	private static final int DEFAULT_SCALE = 2;

	// 这个类不能实例化
	private MoneyUtil() {
	}
	private BigDecimal root;
	
	public static MoneyUtil obtain(Number number){
		MoneyUtil moneyUtil = new MoneyUtil();
		moneyUtil.root = objectToBigDecimal(number);
		return moneyUtil;
	}
	
	public MoneyUtil subtract(Number number){
		root.subtract(objectToBigDecimal(number));
		return this;
	}
	
	public MoneyUtil multiply(Number number){
		root.multiply(objectToBigDecimal(number));
		return this;
	}
	
	public MoneyUtil divide(Number number){
		root.divide(objectToBigDecimal(number));
		return this;
	}
	
	public MoneyUtil add(Number number){
		root.add(objectToBigDecimal(number));
		return this;
	}
	
	public BigDecimal create(){
		return objectToBigDecimal(root);
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
		}else{//未知的类型
			throw new IllegalArgumentException("unknown type!");
		}
		return value;
	}

	/** 加法 */
	public static double add(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	/** 减法 */
	public static double subtract(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	/** 乘法 */
	public static double multiply(Object v1, Object v2) {
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/** 除法 */
	public static double divide(Object v1, Object v2) {
		return divide(v1, v2, DEFAULT_SCALE);
	}

	/**
	 * 除法
	 * @param v1
	 * @param v2
	 * @param scale 运算精度
	 * @return
	 */
	public static double divide(Object v1, Object v2, int scale) {
		if (scale < 0) {//运算精度必须大于等于0
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = objectToBigDecimal(v1);
		BigDecimal b2 = objectToBigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 四舍五入
	 * @param v
	 * @param scale 运算精度
	 * @return
	 */
	public static double round(Object v, int scale) {
		if (scale < 0) {//运算精度必须大于等于0
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = objectToBigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 去掉小数点后无效的0
	 * @param price
	 * @return
	 */
    public static String replace(double price){
        String s = String.valueOf(price);
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return s;
    }
}
