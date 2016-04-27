package com.classic.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 偏好参数存储工具类
 *
 * @author 续写经典
 * @date 2015/11/3
 */
public class SharedPreferencesUtil {
    private SharedPreferencesUtil() { }

    private SharedPreferences mSharedPreferences;
    private Editor mEditor;

    public SharedPreferencesUtil(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public SharedPreferences getSP() {
        return mSharedPreferences;
    }

    public Editor getEditor() {
        return mEditor;
    }

    /**
     * 存储数据(Long)
     */
    public void putLongValue(String key, long value) {
        mEditor.putLong(key, value).commit();
    }

    /**
     * 存储数据(Int)
     */
    public void putIntValue(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    /**
     * 存储数据(String)
     */
    public void putStringValue(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    /**
     * 存储数据(boolean)
     */
    public void putBooleanValue(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    /**
     * 取出数据(Long)
     */
    public long getLongValue(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 取出数据(int)
     */
    public int getIntValue(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 取出数据(boolean)
     */
    public boolean getBooleanValue(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 取出数据(String)
     */
    public String getStringValue(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        mEditor.clear().commit();
    }

    /**
     * 移除指定数据
     */
    public void remove(String key) {
        mEditor.remove(key).commit();
    }
}
