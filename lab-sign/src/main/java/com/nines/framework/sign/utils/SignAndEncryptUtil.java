package com.nines.framework.sign.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class SignAndEncryptUtil {
    private static final String signature_algorithm = "SHA256withRSA"; // 签名算法
    private static final String encryptAlgorithm = "RSA"; // 加密算法
    private static final String decryptAlgorithm = "RSA"; // 解密算法
    private static final String charset = "UTF-8";
    private static final int max_encrypt_block = 234; //2048位rsa单次最大加密长度
    private static final int max_decrypt_block = 256; //2048位rsa单次最大解密长度

    private static PublicKey sign_public_key = null;
    private static PrivateKey sign_private_key = null;

    private static PublicKey crypt_public_key = null;
    private static PrivateKey crypt_private_key = null;

    static {
        // 静态加载，提高效率，但是配置的修改都需要重启server才能生效
        // 对方base64后公钥字符串,用于验签
        String sign_pub_key_str = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8/T8g1bcdepNumS4h4OPxWb3flCwJodFmSpxhf4N7zMZLUVP15fXzMyNcY2o3QcmpmjwPbtIG9Q3S3bJJSm3m1mbTauBDXFs3ijIhmKPf8KDRPC+If60utal2gndbwR5agIzKZyWiTCshqlh6AqBz5NeYjbz1MmRc3Tvg6F/JQPzxyGwOBz/p3C3nCMc7M9SNCFhaxtk2fg93l1YfiNqnbY5t5hPiNn9lifePhpi9W4MuIZ6ERTfu3Aw4jPavIkYlbJALeC4o9aySImehD7Mzo9bqOi+BlCNyuTx/KoEHbQAPNncO/d0gYKZO82nPegZUgzjXmXlscYUjouxmu36wQIDAQAB";
        // 己方base64后私钥字符串,用于签名
        String sign_pri_key_str = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDz9PyDVtx16k26ZLiHg4/FZvd+ULAmh0WZKnGF/g3vMxktRU/Xl9fMzI1xjajdByamaPA9u0gb1DdLdsklKbebWZtNq4ENcWzeKMiGYo9/woNE8L4h/rS61qXaCd1vBHlqAjMpnJaJMKyGqWHoCoHPk15iNvPUyZFzdO+DoX8lA/PHIbA4HP+ncLecIxzsz1I0IWFrG2TZ+D3eXVh+I2qdtjm3mE+I2f2WJ94+GmL1bgy4hnoRFN+7cDDiM9q8iRiVskAt4Lij1rJIiZ6EPszOj1uo6L4GUI3K5PH8qgQdtAA82dw793SBgpk7zac96BlSDONeZeWxxhSOi7Ga7frBAgMBAAECggEBAOK9oLSx1tYBn5zOYHQTY10mBoHwnMgykg151WmkZ28bHxtkgIDxMDG5LP2NnJVSQCoK/r9hgfPbmkkZbnqL+4r4DdByxs+nzHfvj/DXSmiEcV1yJEWUZoYBWsT8xkKY9gABa2wRjhiUlMXUSJtSqLQ1VFQUnyv27KlS1gqR34qWAT7/aZfyRQVr2WSChQRSIF2XymRbKB4LSb8UU1EeXysV3Wudj7OaGz7fcMnjfuWQ2JHSKBtEnikNPhYn/OYYnYyEq32NLX1jpIF9thZPCKCPNeTLaMp64CnI1kqgCbAkFT8Wvpow6RdA1tyn1r8KBGiH97xazNfkYdkY21Jo5AECgYEA+6fjlmPYQ8JlREM1taFku6GFEoa6kwhAy21JIqaBcVM/PQLgIEQrP+ATRC7O6xN59mwnaLbhR4W/IO2GFK2eIVe3n0VficEv7pg2/w4HzTSCpu7nupu1sswkfDlRaV8Frd44/P7CJneiyKzvjiF68BEjFNgLxz2dr+GfH3KHsakCgYEA+CsTKar/auZizmUdpSF+NDHyJVnl+ofeJawPfj3nkhkWDU8iUPPyqbOul95j11TxVoFzEJ+cl0cxzj5aceBLT9lN+mbA4nN9zfSiRp1lmne40yvpJtRde9MSf3+9HfAuq0DsVIJ43y9sGEBU1nFaYqtxo702gggXNHjDvCNn31kCgYAhvr2R6QmS7HpP0QNeu6maT2pctZxHIxAtNxZbgyBQZe1Q7RXIvdte0VlEB1LPyujL1mziacODp7edXrxgAXf22KBSxyasfi9DOz4ErYj2R1hNsjN4nnCa7SznS3hTPN6yFd5mbnLyXZCCgE4PLQYjMukVXnMV4HlIoGSJ/v2bIQKBgQDQRJYYagFg3Wgefo2af7lCZIgrAH8Y+d1dEEEQWTN2UMdI9BiAh7om4TytB14S4J6JUqoWUV1BN+Z8h6ev2eRaZqwicQl/Ah2aMqvdR0p1j/P0hO1uQNsTh9gGWFWGQoNoBVxQsA5wf4Pf9l08enS4Sdlc6PZNuix/RLo/shcCQQKBgF6I9vDyMTE0cM5jiCw9WN3J5fxzSSoAcmUlREpGb4kvzgZLDNZ+MjhJSHPMJDv6GM0a5WhGqpVQS0WPqI+1xiQ473c3xIGpmeD7TgaHuhQY11q6U7e5z1asOK17/YMJXTAk9aN8L18m8rz4xe2Ab/A2yUoMXF9ARWllvGS2IhvI";

        // 对方base64后公钥字符串,用于加密
        String crypt_pub_key_str = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4YrpZiSn8vU3jMyHAkBn+swY/GDnYKtSyWsn2xHXmExNDHiqFAIWinok3J5PV/K2IVtAywG0st+kmPP5W8b0F3UeHwfQj8XnqdhmWUvUbnSqjTEu9k8fhiQCw9ltBi7i2M+H9SQSHTMMh5UgIqkDeV3yB9QkEmuoth2V2Ls3NFjyGrZOjvgwALfXBzemNPlvvBzA+EveAcEN/bwa77Gc5eXKAME4SPcAz2VuwOmgc9evOJKWhks+Kpiv1uI+Z886hLGa+iz0yZbGv2ZKEgU9mEL7/pv6Ps3Y9RQQbVs9O9sI6Djnoi9LobYWjHOzSvTy2WYNkfuJCwkyLAWt6IrmQIDAQAB";
        // 己方base64后私钥字符串,用于解密
        String crypt_pri_key_str = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDhiulmJKfy9TeMzIcCQGf6zBj8YOdgq1LJayfbEdeYTE0MeKoUAhaKeiTcnk9X8rYhW0DLAbSy36SY8/lbxvQXdR4fB9CPxeep2GZZS9RudKqNMS72Tx+GJALD2W0GLuLYz4f1JBIdMwyHlSAiqQN5XfIH1CQSa6i2HZXYuzc0WPIatk6O+DAAt9cHN6Y0+W+8HMD4S94BwQ39vBrvsZzl5coAwThI9wDPZW7A6aBz1684kpaGSz4qmK/W4j5nzzqEsZr6LPTJlsa/ZkoSBT2YQvv+m/o+zdj1FBBtWz072wjoOOeiL0uhthaMc7NK9PLZZg2R+4kLCTIsBa3oiuZAgMBAAECggEAcUqtZiMaidMGNbBbxZJsd9gbArwS5yadJ1H4dArNHxsRSyh9WQY3a5MV54x/qsJM9F1aHZVa+1CWYnrfpX4js0RftLkWI/Y83kjH5V4neHpcYIi7viPN28BEs4I71PpI8jQ2Wyy6a+9PxQwjNeY2klHK7/MpbmBJsbRLm9J2vW3UKHIsmK8enngMJQei/dWXyT0wAiR+vSk6NrZ57T067cyTP9I3c8XsP7AakPqY9R/sJ7ebkcw9dEat6EC/hcZyrj158YbWe1zvKn51KTYYS0FHYf8a8XukzYkM8WHcPAD4Od+PNP0ERvoYEV1FVx7ScFV2/itU933B4CoO+zJGkQKBgQC4/ftqErQ40piT0VCDb3uHctUhZvebyEsqZF2pG1Isv/nAIrid+IjBnfg2E25gzMTf6M0a3X5W+rLtPOji+fbM4F9/c94jeuQe2UTY5Th/Hge6A1WyOkcIPyEFHQWSO2R4lE+CRqhrGzQE0PC6VUja3g5Gsvh7WlyU+h61yYcCuwKBgQC2AjihcRYI1qgMLTyaHcmtXy9SH7ZwLio2l1rgX/kyh5g/jkD9sQ6dNX2PGiVKImrBQZc/XtIqB3PwbSPoH4UQXjqPL53WObuhWYNZuNMaIQIA6uQPnXEO8NTpnYlRtcz1lBuOWCpcCLOIAazMCUpgoiLO5Lnbgwl5brOzuvc3uwKBgAWtLXjMyCDyPWJTyu+tjRHT8TxMJ47tL3lGiSU2RGhBH83Igve995xRV5t5LyrjPatNPnXzk/vhR3pGIzNLzkntHKERkiBule9oZVdueTRVXJFVptyIM0PKhB3fDRu2nNeiXWo29tYKiKcZLjnZghnNNIzw0vUeCbc8kZKsc2sfAoGAeKhkTnIff2B+KwRaJnkU18BAKJspYJjaFzKjZhd5pgvJ3Gg7oXKdE48AW7ujeM/0IYWP9MyxWndyXrKbmSg8GCu/emjAo+HGByLGNtaV675yC5Wr9sYSyyrQmsZ64BY7d5nl4qmD+1sxWTpzCRgnnIQfuzRaGbF2uG+w5bdOaD0CgYB9NwHjlHjgJ8h2E2UGdgYkzK3W+kJbnlQAdV+Uv+K5IyuCIre7BLxzutTAY5sKJNnNMGha8jcJVJbKHvE+PFdu9ckjT1rJU0YMlbiAkGqHUMh9+68Nbkhn3DIO0PCkrkWwAzuk0GQb05Ka7kjy7FgyjD5HX4PhgUIeWRUF+NfAvQ==";

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            sign_public_key = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(sign_pub_key_str))); // 用于验签
            sign_private_key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(sign_pri_key_str))); // 用于签名

            crypt_public_key = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(crypt_pub_key_str))); // 用于加密
            crypt_private_key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(crypt_pri_key_str))); // 用于解密
        } catch (Exception e) {
            //日志记录
        }
    }

    public static String sign(String param) {
        try {
            if (sign_private_key == null) {
                throw new RuntimeException("私钥未初始化");
            }
            Signature signature = Signature.getInstance(signature_algorithm);
            signature.initSign(sign_private_key);
            signature.update(param.getBytes(charset));
            return Base64Utils.encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名异常", e);
        }
    }

    public static String encrypt(String param) {
        if (crypt_public_key == null) {
            throw new RuntimeException("公钥未初始化");
        }
        if (StringUtils.isEmpty(param)) {
            throw new IllegalArgumentException("待加密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(encryptAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, crypt_public_key);
            byte[] data = param.getBytes(charset);
            int inputLen = data.length;

            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen > max_encrypt_block + offSet) {
                    cache = cipher.doFinal(data, offSet, max_encrypt_block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * max_encrypt_block;
            }
            return Base64Utils.encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("加密异常", e);
        }
    }

    public static boolean veriSign(String param, String sign) {
        try {
            if (sign_public_key == null) {
                throw new RuntimeException("公钥未初始化");
            }
            Signature signature = Signature.getInstance(signature_algorithm);
            signature.initVerify(sign_public_key);
            signature.update(param.getBytes(
                     charset));
            return signature.verify(Base64Utils.decodeFromString(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签失败", e);
        }
    }

    public static String decrypt(String param) {
        if (crypt_private_key == null) {
            throw new RuntimeException("私钥未初始化");
        }
        if (StringUtils.isEmpty(param)) {
            throw new IllegalArgumentException("待解密数据为空");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(decryptAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, crypt_private_key);
            byte[] data = Base64Utils.decodeFromString(param);
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen > max_decrypt_block + offSet) {
                    cache = cipher.doFinal(data, offSet, max_decrypt_block);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                i++;
                out.write(cache, 0, cache.length);
                offSet = i * max_decrypt_block;
            }
            return new String(out.toByteArray(), charset);
        } catch (Exception e) {
            throw new RuntimeException("解密处理异常", e);
        }
    }

    public static void main(String[] args){
        //generateKey();
        requestDemo();
        //serverDemo();
    }

    private static void requestDemo() {
        try {
            String param = new JSONObject().fluentPut("name", "张三").toJSONString();
            String data = encrypt(param);
            String sign = sign(data);
            JSONObject jsonObject = new JSONObject().fluentPut("sign", sign).fluentPut("data", data);

            HttpPost httpPost = new HttpPost("url");
            httpPost.setEntity(new StringEntity(jsonObject.toJSONString(), "application/json", "utf-8"));
            HttpClient httpClient = HttpClientBuilder.create().build();
            httpClient.execute(httpPost);
            ((CloseableHttpClient) httpClient).close();
        } catch (IOException ignore) {

        }
    }

    private static String serverDemo() {
        /***** 以下为服务端示例,仅供参考  *****/
        // http获取请求头, 请求体
        String requestBody = "{\"data\":\"HNCwdIV6bb8LuUHG9xP2AT24t9aeFGyJg0gh5L4qAC3Kb6QgsrO/3WVX8FrIbgwY9q3xkKsNclN1SGzcRZQjthg2FRN6JI7B4QyQLRFvSoiKiAeL5O/otO9j1pc4lf9CHnPeR++ODIccmAepRkuUT7hZKXPkEiY+eqxxedFrkf591FLiz2yEOLetp3piyu1ezB7TtvYxsw2QjiNO+TL8ehQZljF1qtOk7dFM6QgSIKdVDII+DrR9Tdcjft/ZjpH4BWqjVOtj24mrxR4kqdi9/UVprON9x1hxK65QSHW3HALkGGFt4Nk3xuDxknipJbSq5vr6GhgiNl3M1Sa8SrgBTw==\",\"sign\":\"ahEuddfXH05RfvcPZQ0G/gt070qOwQP1KXjddPrJCSlXZOkGxd0FkgjYIv62n4QvrAGaOQ7tUNG9TbV7LNbKsc8EH/5YmzTgmRoDERQAcm6PccgD0n51TjI9QPupA4P+UAlelcD7E/QIRCoedKinPXqMhfZjI3HfnSMB72moPnkQ1iW9Uv5AikNXQdffPt2OK/nLbPaOzWdhtveKwADRomCGABBFFnophw8ReX5UY1IsYKId7CGMqi8gsLm6ycuCJu2oMkO3B6Rq7Pirbmmu59BTcjclguBhe/VI1VCGU9S1j3dA8aauJONon8T+z3672I8ax7hc1GO/R8GS+t5OXA==\"}"; // @RequestBody json结构

        // 接收请求获取参数
        JSONObject requestParams = JSONObject.parseObject(requestBody);

        /*取参数及签名*/
        String data = requestParams.getString("data");
        String sign = requestParams.getString("sign");

        /*验签*/
        boolean f = veriSign(data, sign);
        if (f) { // 验签通过才需要解密,否则认为非法请求
            /*解密*/
            String dec = decrypt(data);
            /*业务逻辑*/
            JSONObject businessResult = doBusiness(dec);

            /*加密*/
            String responseParam = encrypt(businessResult.toJSONString());
            /*签名*/
            String responseSign = sign(responseParam);
            Map<String, String> response = new HashMap<>(2);
            response.put("data", responseParam);
            response.put("sign", responseSign);
            /*返回*/
            return JSONObject.toJSONString(response);
        }else {
            return null;
        }
    }

    private static JSONObject doBusiness(String dec) {
        /*响应结果*/
        return new JSONObject();
    }

    private static void generateKey() {
        /***** 以下为密钥生成方案,仅供参考  *****/
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(encryptAlgorithm);
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            System.out.println("privateKey: " + Base64Utils.encodeToString(privateKey.getEncoded()));
            System.out.println("publicKey:  " + Base64Utils.encodeToString(publicKey.getEncoded()));
        } catch (NoSuchAlgorithmException ignore) {

        }
    }
}
