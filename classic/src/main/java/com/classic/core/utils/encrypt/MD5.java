package com.classic.core.utils.encrypt;

import java.security.MessageDigest;

/**
 * MD5工具类
 */
public final class MD5 {
	private MD5() { }
	
	public static String byteArrayToHexString(byte byteArray[]) {
		StringBuffer strHex = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
			strHex.append(byteToHexString(byteArray[i]));
		return strHex.toString();
	}
	
	private static String byteToHexString(byte bt) {
		int i = bt;
		if (i < 0) i += 256;
		int j = i / 16;
		int k = i % 16;
		return hexDigits[j] + hexDigits[k];
	}
	
	public static String MD5Encode(String strOrigin) {
		String strAim = null;
		try {
			strAim = new String(strOrigin);
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			strAim = byteArrayToHexString(messageDigest.digest(strAim.getBytes()));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return strAim;
	}
	
	public static byte[] MD5Encode2ByteArray(String strOrigin) {
		String strAim = null;
		byte[] byteAim = null;
		try {
			strAim = new String(strOrigin);
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byteAim = messageDigest.digest(strAim.getBytes());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return byteAim;
	}
	
	private static final String hexDigits[] = {
	        "0",
	        "1",
	        "2",
	        "3",
	        "4",
	        "5",
	        "6",
	        "7",
	        "8",
	        "9",
	        "a",
	        "b",
	        "c",
	        "d",
	        "e",
	        "f"
	                                        };
}
