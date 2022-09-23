package com.nines.framework.sign.config;

import com.nines.framework.sign.constant.SignAndEncryptConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author tanyujie
 * @classname MySignAndEncryptUtil
 * @description 请求参数 加密加签 解密解签 工具类
 * @date 2022/8/17 17:35
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({KeyFactory.class})
@EnableConfigurationProperties(SignAndEncryptSecretKeyProperties.class)
@ConditionalOnProperty(prefix = "sign", name = "enable", havingValue = "true")
public class SignAndEncryptInitConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 签名公钥
     */
    private PublicKey signPublicKey;
    /**
     * 签名私钥
     */
    private PrivateKey signPrivateKey;
    /**
     * 加密公钥
     */
    private PublicKey cryptPublicKey;
    /**
     * 加密私钥
     */
    private PrivateKey cryptPrivateKey;

    @PostConstruct
    public void init(SignAndEncryptSecretKeyProperties properties) {
        try {
            logger.info("MySignAndEncryptUtil加密加签类 密钥开始加载......");
            KeyFactory keyFactory = KeyFactory.getInstance(SignAndEncryptConstant.ENCRYPT_DECRYPT_ALGORITHM);
            // 签名
            signPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(properties.getSignPublicKeyStr())));
            signPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(properties.getSignPrivateKeyStr())));
            // 参数
            cryptPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(properties.getCryptPublicKeyStr())));
            cryptPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(properties.getCryptPrivateKeyStr())));
            logger.info("MySignAndEncryptUtil加密加签类 密钥加载完成......");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.info("MySignAndEncryptUtil加密加签类 密钥加载失败......", e);
        }
    }

    public PublicKey getSignPublicKey() {
        return signPublicKey;
    }

    public PrivateKey getSignPrivateKey() {
        return signPrivateKey;
    }

    public PublicKey getCryptPublicKey() {
        return cryptPublicKey;
    }

    public PrivateKey getCryptPrivateKey() {
        return cryptPrivateKey;
    }
}
