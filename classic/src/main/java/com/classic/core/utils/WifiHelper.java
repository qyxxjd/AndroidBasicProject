package com.classic.core.utils;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.RequiresPermission;
import java.util.List;

/**
 * wifi工具类
 * 需要权限：
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 *
 * @author 续写经典
 * @date 2015/11/3
 */
public final class WifiHelper {

    /** wifi加密类型：无加密 */
    public static final int WIFI_ENCRYPTION_TYPE_NONE
            = WifiConfiguration.KeyMgmt.NONE;
    /** wifi加密类型：WEP/IEEE8021X加密 */
    public static final int WIFI_ENCRYPTION_TYPE_WEP
            = WifiConfiguration.KeyMgmt.IEEE8021X;
    /** wifi加密类型：PSK加密 */
    public static final int WIFI_ENCRYPTION_TYPE_PSK
            = WifiConfiguration.KeyMgmt.WPA_PSK;
    /** wifi加密类型：EAP加密 */
    public static final int WIFI_ENCRYPTION_TYPE_EAP
            = WifiConfiguration.KeyMgmt.WPA_EAP;

    private volatile static WifiHelper sWifiHelper;
    private Context mContext;
    private WifiManager mWifiManager;

    private WifiHelper(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) context.getSystemService(
                Context.WIFI_SERVICE);
    }

    public static final WifiHelper getInstance(Context context) {
        if (null == sWifiHelper) {
            synchronized (WifiHelper.class) {
                if (null == sWifiHelper) {
                    sWifiHelper = new WifiHelper(context);
                }
            }
        }
        return sWifiHelper;
    }


    /**
     * 获取wifi加密类型
     * @param scanResult
     * @return <pre>
     *      WIFI_ENCRYPTION_TYPE_WEP:WEP加密
     *      WIFI_ENCRYPTION_TYPE_PSK:PSK加密
     *      WIFI_ENCRYPTION_TYPE_EAP:EAP加密
     *      WIFI_ENCRYPTION_TYPE_NONE:无加密
     *  </pre>
     */
    public static int getEncryptionType(ScanResult scanResult) {
        if (scanResult.capabilities.contains("WEP")) {
            return WIFI_ENCRYPTION_TYPE_WEP;
        }
        else if (scanResult.capabilities.contains("PSK")) {
            return WIFI_ENCRYPTION_TYPE_PSK;
        }
        else if (scanResult.capabilities.contains("EAP")) {
            return WIFI_ENCRYPTION_TYPE_EAP;
        }
        return WIFI_ENCRYPTION_TYPE_NONE;
    }


    /**
     * 启用/禁用 wifi
     * @param enabled
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public void setEnabled(boolean enabled) {
        if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING ||
                mWifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING){
            return;
        }
        mWifiManager.setWifiEnabled(enabled);
    }

    /**
     * wifi是否打开
     * @return
     */
    public boolean isEnabled() {
        return mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING ||
               mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }


    /**
     * 获取已连接过的wifi配置列表
     */
    public List<WifiConfiguration> getConfiguration() {
        return mWifiManager.getConfiguredNetworks();
    }


    /**
     * 扫描WiFi列表
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public List<ScanResult> getScanResults() {
        List<ScanResult> list = null;
        //开始扫描WiFi
        boolean f = mWifiManager.startScan();
        if (!f) {
            getScanResults();
        }
        else {
            // 得到扫描结果
            list = mWifiManager.getScanResults();
        }

        return list;
    }


    /**
     * 获取当前连接的WIFI信息
     */
    public WifiInfo getConnectionInfo() {
        return mWifiManager.getConnectionInfo();
    }


    /**
     * 连接未加密wifi
     * @param scanResult
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean connection(ScanResult scanResult) {
        final WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + scanResult.SSID + "\"";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        return connection(config);
    }


    /**
     * 连接加密wifi
     * @param scanResult
     * @param password
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean connection(ScanResult scanResult, String password) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + scanResult.SSID + "\"";
        wifiConfiguration.preSharedKey = "\"" + password + "\"";
        wifiConfiguration.hiddenSSID = true;
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.allowedAuthAlgorithms.set(
                WifiConfiguration.AuthAlgorithm.OPEN);
        wifiConfiguration.allowedGroupCiphers.set(
                WifiConfiguration.GroupCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(
                WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedKeyManagement.set(
                getEncryptionType(scanResult));
        wifiConfiguration.allowedPairwiseCiphers.set(
                WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedPairwiseCiphers.set(
                WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        return connection(wifiConfiguration);
    }


    /**
     * 连接wifi
     * @param wifiConfiguration
     * @return
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean connection(WifiConfiguration wifiConfiguration) {
        int networkId = mWifiManager.addNetwork(wifiConfiguration);
        if (networkId != -1) {
            boolean result = mWifiManager.enableNetwork(networkId, true);
            if(result){
                mWifiManager.saveConfiguration();
            }
            return result;
        }
        return false;
    }


    /**
     * 是否配置过当前wifi
     * @param scanResult
     * @return
     */
    public WifiConfiguration existConfig(ScanResult scanResult) {
        // 查看该网络是否已经配置过
        for (WifiConfiguration config : getConfiguration()) {
            if (config.SSID.equals("\"" + scanResult.SSID + "\"")) {
                return config;
            }
        }
        return null;
    }


    /**
     * 获取mac地址
     * @return
     */
    public String getMacAddress(){
        WifiInfo info = mWifiManager.getConnectionInfo();
        return info != null ? info.getMacAddress() : "";
    }


    /**
     * 断开指定ID的wifi
     * @param networkId
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public void disconnect(int networkId) {
        mWifiManager.disableNetwork(networkId);
        mWifiManager.disconnect();
    }
}
