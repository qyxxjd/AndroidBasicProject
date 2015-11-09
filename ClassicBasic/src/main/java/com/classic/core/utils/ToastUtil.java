package com.classic.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class ToastUtil {
  private ToastUtil(){}

  public static void showToast(Context context,String text) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
  }

  public static void showToast(Context context,int resId) {
    Toast.makeText(context,context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
  }
  public static void showLongToast(Context context,String text) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
  }

  public static void showLongToast(Context context,int resId) {
    Toast.makeText(context,context.getResources().getText(resId), Toast.LENGTH_LONG).show();
  }
}
