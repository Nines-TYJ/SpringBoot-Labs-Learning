package com.nines.framework.sign.utils;

import com.nines.framework.sign.config.SignAndEncryptInitConfig;
import com.nines.framework.sign.constant.SignAndEncryptConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Signature;

/**
 * @author tanyujie
 * @classname SignAndEncryptUtils
 * @description 加解密方法工具类
 * @date 2022/8/18 10:10
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
@ConditionalOnClass({SignAndEncryptConstant.class, SignAndEncryptInitConfig.class})
public class SignAndEncryptUtils {

    private final SignAndEncryptInitConfig signAndEncryptInitConfig;

    public String sign(String param) {
        try {
            if (signAndEncryptInitConfig.getSignPrivateKey() == null) {
                throw new RuntimeException("私钥未初始化");
            }
            Signature signature = Signature.getInstance(SignAndEncryptConstant.SIGNATURE_ALGORITHM);
            signature.initSign(signAndEncryptInitConfig.getSignPrivateKey());
            signature.update(param.getBytes(SignAndEncryptConstant.CHARSET));
            return Base64Utils.encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名异常", e);
        }
    }

    public String encrypt(String param) {
        if (signAndEncryptInitConfig.getCryptPublicKey() == null) {
            throw new RuntimeException("公钥未初始化");
        }
        if (!StringUtils.hasText(param)) {
            throw new IllegalArgumentException("待加密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(SignAndEncryptConstant.ENCRYPT_DECRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, signAndEncryptInitConfig.getCryptPublicKey());
            byte[] data = param.getBytes(SignAndEncryptConstant.CHARSET);
            int inputLen = data.length;

            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen > SignAndEncryptConstant.MAX_ENCRYPT_BLOCK + offSet) {
                    cache = cipher.doFinal(data, offSet, SignAndEncryptConstant.MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * SignAndEncryptConstant.MAX_ENCRYPT_BLOCK;
            }
            return Base64Utils.encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("加密异常", e);
        }
    }

    public boolean verifySign(String param, String sign) {
        try {
            if (signAndEncryptInitConfig.getSignPublicKey() == null) {
                throw new RuntimeException("公钥未初始化");
            }
            Signature signature = Signature.getInstance(SignAndEncryptConstant.SIGNATURE_ALGORITHM);
            signature.initVerify(signAndEncryptInitConfig.getSignPublicKey());
            signature.update(param.getBytes(SignAndEncryptConstant.CHARSET));
            return signature.verify(Base64Utils.decodeFromString(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        }
    }

    public String decrypt(String param) {
        if (signAndEncryptInitConfig.getCryptPrivateKey() == null) {
            throw new RuntimeException("私钥未初始化");
        }
        if (!StringUtils.hasText(param)) {
            throw new IllegalArgumentException("待解密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(SignAndEncryptConstant.ENCRYPT_DECRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, signAndEncryptInitConfig.getCryptPrivateKey());
            byte[] data = Base64Utils.decodeFromString(param);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen > SignAndEncryptConstant.MAX_DECRYPT_BLOCK + offSet) {
                    cache = cipher.doFinal(data, offSet, SignAndEncryptConstant.MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                i++;
                out.write(cache, 0, cache.length);
                offSet = i * SignAndEncryptConstant.MAX_DECRYPT_BLOCK;
            }
            return out.toString(SignAndEncryptConstant.CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密处理异常", e);
        }
    }

}
