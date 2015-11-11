package com.classic.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 常用的数据非空检查工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class DataUtil {
  private DataUtil(){}

  /** 检查数组是否为空(去掉前后空格) */
  public static boolean isEmpty(String string){
    return (string == null || string.length() == 0 || string.trim().length() == 0);
  }
  /** 获取字符串长度(去掉前后空格) */
  public static int getSize(String string) {
    return string == null  ? 0 : string.trim().length();
  }

  /** 检查数组是否为空 */
  public static <V> boolean isEmpty(V[] sourceArray) {
    return (sourceArray == null || sourceArray.length == 0);
  }
  /** 获取数组长度 */
  public static <V> int getSize(V[] sourceArray) {
    return sourceArray == null ? 0 : sourceArray.length;
  }

  /** 检查Collection是否为空 */
  public static <V> boolean isEmpty(Collection<V> c) {
    return (c == null || c.size() == 0);
  }
  /** 获取Collection长度 */
  public static <V> int getSize(Collection<V> c) {
    return c == null ? 0 : c.size();
  }

  /** 检查List是否为空 */
  public static <V> boolean isEmpty(List<V> sourceList) {
    return (sourceList == null || sourceList.size() == 0);
  }
  /** 获取List长度 */
  public static <V> int getSize(List<V> sourceList) {
    return sourceList == null ? 0 : sourceList.size();
  }

  /** 检查Map是否为空 */
  public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
    return (sourceMap == null || sourceMap.size() == 0);
  }
  /** 获取Map长度 */
  public static <K, V> int getSize(Map<K, V> sourceMap) {
    return sourceMap == null ? 0 : sourceMap.size();
  }
}
