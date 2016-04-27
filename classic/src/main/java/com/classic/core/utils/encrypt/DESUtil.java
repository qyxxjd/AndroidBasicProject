/**
 * =================================================
 *
 * @copyright 杭州龙骞科技有限公司 2012-1014
 * =================================================
 */
package com.classic.core.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密解密工具类
 */
public final class DESUtil {
    private DESUtil(){}

    private static final String ALGORITHM = "DES";
    private static final String DEFAULT_KEY = "classic6";
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";


    /**
     * 加密文本
     *
     * @param text 要加密的内容
     * @return
     * @throws Exception
     */
    public static String encrypt(String text) throws Exception {
        return encrypt(text, DEFAULT_KEY);
    }


    /**
     * 加密文本
     *
     * @param text 要加密的内容
     * @param key 加密key
     * @return
     * @throws Exception
     */
    public static String encrypt(String text, String key) throws Exception {
        return encrypt(text, key, DEFAULT_CHARSET_NAME);
    }


    /**
     * 加密文本
     *
     * @param text 要加密的内容
     * @param key 加密key
     * @param charsetName 编码格式
     * @return
     * @throws Exception
     */
    public static String encrypt(String text, String key, String charsetName) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
        byte[] data = c.doFinal(text.getBytes(charsetName));
        return new String(Base64.encode(data));
    }

    /**
     * 解密文本
     *
     * @param text 要解密的内容
     * @return
     * @throws Exception
     */
    public static String decrypt(String text) {
    	return decrypt(text, DEFAULT_KEY);
    }
    
    /**
     * 解密文本
     *
     * @param text 要解密的内容
     * @param key 加密key
     * @return
     * @throws Exception
     */
    public static String decrypt(String text, String key) {
        return decrypt(text, key, DEFAULT_CHARSET_NAME);
    }


    /**
     * 解密文本
     *
     * @param text 要解密的内容
     * @param key 加密key
     * @param charsetName 编码格式
     * @return
     * @throws Exception
     */
    public static String decrypt(String text, String key, String charsetName) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
            byte[] data = c.doFinal(Base64.decode(text.getBytes()));
            return new String(data, charsetName);
        } catch (Exception e) {
            return null;
        }
    }
}