package com.nines.framework.sign.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tanyujie
 * @classname SignAndEncryptSecretKeyProper
 * @description 密钥
 * @date 2022/8/17 17:43
 * @since 1.0
 */
@ConfigurationProperties("sign")
@Data
public class SignAndEncryptSecretKeyProperties {

    /**
     * 签名公钥
     */
    private String signPublicKeyStr;
    /**
     * 签名私钥
     */
    private String signPrivateKeyStr;
    /**
     * 加密公钥
     */
    private String cryptPublicKeyStr;
    /**
     * 加密私钥
     */
    private String cryptPrivateKeyStr;

}
