/*
 * ========================================================
 * Copyright(c) 2014 杭州龙骞科技-版权所有
 * ========================================================
 * 本软件由杭州龙骞科技所有, 未经书面许可, 任何单位和个人不得以
 * 任何形式复制代码的部分或全部, 并以任何形式传播。
 * 公司网址
 * http://www.hzdracom.com/
 * ========================================================
 */
package com.classic.core.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.classic.core.log.Logger;
import java.util.UUID;

/**
 * 设备信息工具类
 * @author 续写经典
 * @date 2015/11/3
 */
public final class DeviceUtil
{
	private Context                 context;
	private static DeviceUtil util;
	private static TelephonyManager tm;
	private DeviceUtil(Context context){
		this.context = context;
	}
	public static DeviceUtil getInstance(Context context){
		if(null==util) util= new DeviceUtil(context);
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return util;
	}

	/**
	 * 打印设备信息（调试专用）
	 */
	public void printInfo(){
		StringBuffer sb = new StringBuffer("设备信息：\n");
		sb.append("getID:").append(getID()).append("\n");
		sb.append("getAndroidId:").append(getAndroidId()).append("\n");
		sb.append("getDeviceId:").append(getDeviceId()).append("\n");
		sb.append("getUUID:").append(getUUID()).append("\n");
		sb.append("getNumber:").append(getNumber()).append("\n");
		sb.append("getSubscriberId:").append(getSubscriberId()).append("\n");
		sb.append("getSimSerialNumber:").append(getSimSerialNumber()).append("\n");
		sb.append("getSerialNumber:").append(getSerialNumber()).append("\n");
		sb.append("getPhoneType:").append(getPhoneType()).append("\n");
		sb.append("getNetworkType:").append(getNetworkType()).append("\n");
		Logger.d(sb.toString());
	}
	
	/**
	 * 获取ANDROID_ID
	 * <pre>
	 * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来.
	 * 在设备恢复出厂设置后，该值可能会改变, ANDROID_ID也可视为作为唯一设备标识号的一个好选择。
	 * </pre>
	 * @return
	 */
	public String getAndroidId(){
		final String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return TextUtils.isEmpty(androidId) ? "" : androidId;
	}
	/**
	 * 获取UUID <BR/>
	 * UUID是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的。
	 * @return
	 */
	public String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取设备绝对的唯一标识<BR/>
	 * 先获取DeviceId,如果为空,获取ANDROID_ID,如果还是空,生成一个UUID
	 * @return
	 */
	public String getID(){
		String result = getDeviceId();
		if(TextUtils.isEmpty(result)){
			result = getAndroidId();
			if(TextUtils.isEmpty(result)){
				result = getUUID();
			}
		}
		return result;
	}
	
	/**
     * 返回当前移动终端的唯一标识
     * <pre>
     * 如果是GSM网络，返回IMEI;
     * 如果是CDMA网络，返回MEID;
     * 权限： 获取DEVICE_ID需要READ_PHONE_STATE权限
     * </pre>
     */
	public String getDeviceId(){
		final String deviceId = tm.getDeviceId();
		return TextUtils.isEmpty(deviceId) ? "" : deviceId;
	}
	
	/**
	 * 返回手机号码<可能为空>
	 * @return
	 */
	public String getNumber(){
		final String number = tm.getLine1Number();
		return TextUtils.isEmpty(number) ? "" : number;
	}
	/**
	 * 返回用户唯一标识，比如GSM网络的IMSI编号
	 * @return
	 */
	public String getSubscriberId(){
		final String subscriberId = tm.getSubscriberId();
		return TextUtils.isEmpty(subscriberId) ? "" : subscriberId;
	}
	/**
	 * 返回手机服务商名字
	 */
	public String getProvidersName()
	{
		String ProvidersName = null;
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		String IMSI = getSubscriberId();
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002"))
		{
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001"))
		{
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003"))
		{
			ProvidersName = "中国电信";
		} else
		{
			ProvidersName = "其他服务商";
		}
		return ProvidersName;
	}
	/**
	 * 返回SIM卡的序列号(IMEI)
	 * 注意：对于CDMA设备，返回的是一个空值！
	 * @return
	 */
	public String getSimSerialNumber(){
		final String simSerialNumber = tm.getSimSerialNumber();
		return TextUtils.isEmpty(simSerialNumber) ? "" : simSerialNumber;
	}
	/**
	 * 返回序列号
	 * <Android 2.3以上可以使用此方法>
	 * @return
	 */
	public String getSerialNumber(){
		final String serialNumber = android.os.Build.SERIAL;
		return TextUtils.isEmpty(serialNumber) ? "" : serialNumber;
	}
	
	/**
     * 返回移动终端的类型
     * <pre>
     * 0：PHONE_TYPE_NONE 手机制式未知,可能是平板
     * 1：PHONE_TYPE_GSM  手机制式为GSM，移动和联通
     * 2：PHONE_TYPE_CDMA 手机制式为CDMA，电信
     * </pre>
     */
	public int getPhoneType(){
		return tm.getPhoneType();
	}
	/**
     * 获取网络类型
     * <pre>
     * NETWORK_TYPE_CDMA   网络类型为CDMA
     * NETWORK_TYPE_EDGE   网络类型为EDGE
     * NETWORK_TYPE_EVDO_0 网络类型为EVDO0
     * NETWORK_TYPE_EVDO_A 网络类型为EVDOA
     * NETWORK_TYPE_GPRS   网络类型为GPRS
     * NETWORK_TYPE_HSDPA  网络类型为HSDPA
     * NETWORK_TYPE_HSPA   网络类型为HSPA
     * NETWORK_TYPE_HSUPA  网络类型为HSUPA
     * NETWORK_TYPE_UMTS   网络类型为UMTS
     * 
     * 在中国，
     * 联通的3G为UMTS或HSDPA，
     * 移动和联通的2G为GPRS或EGDE，
     * 电信的2G为CDMA，
     * 电信的3G为EVDO
     * </pre>
     */
	public int getNetworkType(){
		return tm.getNetworkType();
	}
	
}
