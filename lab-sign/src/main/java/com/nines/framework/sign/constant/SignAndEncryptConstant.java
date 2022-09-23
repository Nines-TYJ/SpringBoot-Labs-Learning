package com.nines.framework.sign.constant;

/**
 * @author tanyujie
 * @classname SignAndEncryptConstant
 * @description 加解密参量类
 * @date 2022/8/18 10:07
 * @since 1.0
 */
public class SignAndEncryptConstant {

    public static final String CHARSET = "UTF-8";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    /**
     * 加密 解密 算法
     */
    public static final String ENCRYPT_DECRYPT_ALGORITHM = "RSA";
    /**
     * 2048位rsa单次最大加密长度
     */
    public static final int MAX_ENCRYPT_BLOCK = 234;
    /**
     * 2048位rsa单次最大解密长度
     */
    public static final int MAX_DECRYPT_BLOCK = 256;

}
