package com.classic.core.utils;

/**
 * 应用名称: AndroidBasicProject
 * 包 名 称: com.classic.core.utils
 *
 * 文件描述: 字节工具类(和硬件交互常用的方法)
 * 创 建 人: 续写经典
 * 创建时间: 2016/7/12 21:09
 */
public final class ByteUtil {

    /**
     * 数字转byte[]
     *
     * @param desc 排序模式。true:按高位到低位顺序排列,false:按低位到高位顺序排列
     */
    public static byte[] intToBytes(int number, boolean desc) {
        int temp = number;
        int length = 0;
        while (temp > 0) {
            temp = temp / 256;
            length++;
        }
        if (length == 0) {
            length = 1;
        }

        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            if (desc) {
                result[i] = (byte) (number >> ((length - i - 1) * 8));
            } else {
                result[i] = (byte) (number >> (i * 8));
            }
        }
        return result;
    }

    /**
     * bytes转数字
     * @param bytes
     * @param desc 排序模式。true:按高位到低位顺序排列,false:按低位到高位顺序排列
     * @return
     */
    public static int bytesToInt(byte[] bytes, boolean desc) {
        if(null == bytes){
            throw new NullPointerException("bytes is null!");
        }
        if(bytes.length > 4){
            throw new IllegalArgumentException( "Illegal length!");
        }
        int value = 0;
        switch (bytes.length){
            case 4:
                value = desc ? bytes[0] << 24 + bytes[1] << 16 + bytes[2] << 8 + bytes[3] :
                        bytes[3] << 24 + bytes[2] << 16 + bytes[1] << 8 + bytes[0];
                break;
            case 3:
                value = desc ? bytes[0] << 16 + bytes[1] << 8 + bytes[2] :
                        bytes[2] << 16 + bytes[1] << 8 + bytes[0];
                break;
            case 2:
                value = desc ? bytes[0] << 8 + bytes[1] :
                        bytes[1] << 8 + bytes[0];
                break;
            default:
                value = bytes[0];
                break;
        }
        return value;
    }

    /**
     * byte[]转十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv).append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制字符串转byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * char转byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
