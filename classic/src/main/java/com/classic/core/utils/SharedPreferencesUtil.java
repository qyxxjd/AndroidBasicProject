package com.classic.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 偏好参数存储工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public class SharedPreferencesUtil {
  private SharedPreferences sp;
  private Editor editor;

  public SharedPreferencesUtil(Context context, String name) {
    sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    editor = sp.edit();
  }

  private SharedPreferencesUtil() {
  }

  public SharedPreferences getSP() {
    return sp;
  }

  public Editor getEditor() {
    return editor;
  }

  /**
   * 存储数据(Long)
   */
  public void putLongValue(String key, long value) {
    editor.putLong(key, value).commit();
  }

  /**
   * 存储数据(Int)
   */
  public void putIntValue(String key, int value) {
    editor.putInt(key, value).commit();
  }

  /**
   * 存储数据(String)
   */
  public void putStringValue(String key, String value) {
    editor.putString(key, value).commit();
  }

  /**
   * 存储数据(boolean)
   */
  public void putBooleanValue(String key, boolean value) {
    editor.putBoolean(key, value).commit();
  }

  /**
   * 取出数据(Long)
   */
  public long getLongValue(String key, long defValue) {
    return sp.getLong(key, defValue);
  }

  /**
   * 取出数据(int)
   */
  public int getIntValue(String key, int defValue) {
    return sp.getInt(key, defValue);
  }

  /**
   * 取出数据(boolean)
   */
  public boolean getBooleanValue(String key, boolean defValue) {
    return sp.getBoolean(key, defValue);
  }

  /**
   * 取出数据(String)
   */
  public String getStringValue(String key, String defValue) {
    return sp.getString(key, defValue);
  }

  /**
   * 清空所有数据
   */
  public void clear() {
    editor.clear().commit();
  }

  /**
   * 移除指定数据
   */
  public void remove(String key) {
    editor.remove(key).commit();
  }
}
