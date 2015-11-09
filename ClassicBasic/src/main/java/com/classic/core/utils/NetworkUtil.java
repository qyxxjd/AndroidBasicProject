package com.classic.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class NetworkUtil {
  private NetworkUtil(){}

  public static final int NONE = 0x00;
  public static final int WIFI = 0x01;
  public static final int CMWAP = 0x02;
  public static final int CMNET = 0x03;

  /**
   * 网络是否可用
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager mgr =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] info = mgr.getAllNetworkInfo();
    if (info != null) {
      for (int i = 0; i < info.length; i++) {
        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 判断是否有网络连接
   */
  public static boolean isNetworkConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }

  /**
   * 判断WIFI网络是否可用
   */
  public static boolean isWifiConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mWiFiNetworkInfo =
          mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable();
      }
    }
    return false;
  }

  /**
   * 判断MOBILE网络是否可用
   */
  public static boolean isMobileConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mMobileNetworkInfo =
          mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      if (mMobileNetworkInfo != null) {
        return mMobileNetworkInfo.isAvailable();
      }
    }
    return false;
  }

  /**
   * 获取当前的网络连接类型 0：没有网络 1：WIFI网络2：wap 网络3：net网络
   */
  public static int getAPNType(Context context) {
    ConnectivityManager connMgr =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo == null) {
      return NONE;
    }
    int nType = networkInfo.getType();

    if (nType == ConnectivityManager.TYPE_MOBILE) {
      if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
        return CMNET;
      } else {
        return CMWAP;
      }
    } else if (nType == ConnectivityManager.TYPE_WIFI) {
      return WIFI;
    }
    return NONE;
  }
}
