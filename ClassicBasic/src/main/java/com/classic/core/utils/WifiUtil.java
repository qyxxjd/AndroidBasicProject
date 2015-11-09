package com.classic.core.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.List;

/**
 * wifi工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class WifiUtil {
  private WifiUtil(){}
  /**
   * 描述：打开wifi.
   */
  public static void setWifiEnabled(Context context, boolean enabled) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    if (enabled) {
      wifiManager.setWifiEnabled(true);
    } else {
      wifiManager.setWifiEnabled(false);
    }
  }

  /**
   * 描述：得到所有的WiFi列表.
   */
  public static List<ScanResult> getScanResults(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    List<ScanResult> list = null;
    //开始扫描WiFi
    boolean f = wifiManager.startScan();
    if (!f) {
      getScanResults(context);
    } else {
      // 得到扫描结果
      list = wifiManager.getScanResults();
    }

    return list;
  }

  /**
   * 描述：根据SSID过滤扫描结果.
   */
  public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    ScanResult scanResult = null;
    //开始扫描WiFi
    boolean f = wifiManager.startScan();
    if (!f) {
      getScanResultsByBSSID(context, bssid);
    }
    // 得到扫描结果
    List<ScanResult> list = wifiManager.getScanResults();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        // 得到扫描结果
        scanResult = list.get(i);
        if (scanResult.BSSID.equals(bssid)) {
          break;
        }
      }
    }
    return scanResult;
  }

  /**
   * 描述：获取连接的WIFI信息.
   */
  public static WifiInfo getConnectionInfo(Context context) {
    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    return wifiInfo;
  }
}
