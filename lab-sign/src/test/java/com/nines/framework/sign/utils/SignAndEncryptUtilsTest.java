package com.nines.framework.sign.utils;

import com.alibaba.fastjson.JSONObject;
import com.nines.framework.sign.config.SignAndEncryptInitConfig;
import com.nines.framework.sign.constant.SignAndEncryptConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname SignAndEncryptUtilsTest
 * @description 加解密工具类测试
 * @date 2022/8/18 10:47
 * @since 1.0
 */
@Slf4j
@SpringBootTest
class SignAndEncryptUtilsTest {

    @MockBean
    SignAndEncryptInitConfig signAndEncryptInitConfig;

    @Resource
    SignAndEncryptUtils signAndEncryptUtils;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String signPublicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8/T8g1bcdepNumS4h4OPxWb3flCwJodFmSpxhf4N7zMZLUVP15fXzMyNcY2o3QcmpmjwPbtIG9Q3S3bJJSm3m1mbTauBDXFs3ijIhmKPf8KDRPC+If60utal2gndbwR5agIzKZyWiTCshqlh6AqBz5NeYjbz1MmRc3Tvg6F/JQPzxyGwOBz/p3C3nCMc7M9SNCFhaxtk2fg93l1YfiNqnbY5t5hPiNn9lifePhpi9W4MuIZ6ERTfu3Aw4jPavIkYlbJALeC4o9aySImehD7Mzo9bqOi+BlCNyuTx/KoEHbQAPNncO/d0gYKZO82nPegZUgzjXmXlscYUjouxmu36wQIDAQAB";
        String signPrivateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDz9PyDVtx16k26ZLiHg4/FZvd+ULAmh0WZKnGF/g3vMxktRU/Xl9fMzI1xjajdByamaPA9u0gb1DdLdsklKbebWZtNq4ENcWzeKMiGYo9/woNE8L4h/rS61qXaCd1vBHlqAjMpnJaJMKyGqWHoCoHPk15iNvPUyZFzdO+DoX8lA/PHIbA4HP+ncLecIxzsz1I0IWFrG2TZ+D3eXVh+I2qdtjm3mE+I2f2WJ94+GmL1bgy4hnoRFN+7cDDiM9q8iRiVskAt4Lij1rJIiZ6EPszOj1uo6L4GUI3K5PH8qgQdtAA82dw793SBgpk7zac96BlSDONeZeWxxhSOi7Ga7frBAgMBAAECggEBAOK9oLSx1tYBn5zOYHQTY10mBoHwnMgykg151WmkZ28bHxtkgIDxMDG5LP2NnJVSQCoK/r9hgfPbmkkZbnqL+4r4DdByxs+nzHfvj/DXSmiEcV1yJEWUZoYBWsT8xkKY9gABa2wRjhiUlMXUSJtSqLQ1VFQUnyv27KlS1gqR34qWAT7/aZfyRQVr2WSChQRSIF2XymRbKB4LSb8UU1EeXysV3Wudj7OaGz7fcMnjfuWQ2JHSKBtEnikNPhYn/OYYnYyEq32NLX1jpIF9thZPCKCPNeTLaMp64CnI1kqgCbAkFT8Wvpow6RdA1tyn1r8KBGiH97xazNfkYdkY21Jo5AECgYEA+6fjlmPYQ8JlREM1taFku6GFEoa6kwhAy21JIqaBcVM/PQLgIEQrP+ATRC7O6xN59mwnaLbhR4W/IO2GFK2eIVe3n0VficEv7pg2/w4HzTSCpu7nupu1sswkfDlRaV8Frd44/P7CJneiyKzvjiF68BEjFNgLxz2dr+GfH3KHsakCgYEA+CsTKar/auZizmUdpSF+NDHyJVnl+ofeJawPfj3nkhkWDU8iUPPyqbOul95j11TxVoFzEJ+cl0cxzj5aceBLT9lN+mbA4nN9zfSiRp1lmne40yvpJtRde9MSf3+9HfAuq0DsVIJ43y9sGEBU1nFaYqtxo702gggXNHjDvCNn31kCgYAhvr2R6QmS7HpP0QNeu6maT2pctZxHIxAtNxZbgyBQZe1Q7RXIvdte0VlEB1LPyujL1mziacODp7edXrxgAXf22KBSxyasfi9DOz4ErYj2R1hNsjN4nnCa7SznS3hTPN6yFd5mbnLyXZCCgE4PLQYjMukVXnMV4HlIoGSJ/v2bIQKBgQDQRJYYagFg3Wgefo2af7lCZIgrAH8Y+d1dEEEQWTN2UMdI9BiAh7om4TytB14S4J6JUqoWUV1BN+Z8h6ev2eRaZqwicQl/Ah2aMqvdR0p1j/P0hO1uQNsTh9gGWFWGQoNoBVxQsA5wf4Pf9l08enS4Sdlc6PZNuix/RLo/shcCQQKBgF6I9vDyMTE0cM5jiCw9WN3J5fxzSSoAcmUlREpGb4kvzgZLDNZ+MjhJSHPMJDv6GM0a5WhGqpVQS0WPqI+1xiQ473c3xIGpmeD7TgaHuhQY11q6U7e5z1asOK17/YMJXTAk9aN8L18m8rz4xe2Ab/A2yUoMXF9ARWllvGS2IhvI";
        String cryptPublicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg4YrpZiSn8vU3jMyHAkBn+swY/GDnYKtSyWsn2xHXmExNDHiqFAIWinok3J5PV/K2IVtAywG0st+kmPP5W8b0F3UeHwfQj8XnqdhmWUvUbnSqjTEu9k8fhiQCw9ltBi7i2M+H9SQSHTMMh5UgIqkDeV3yB9QkEmuoth2V2Ls3NFjyGrZOjvgwALfXBzemNPlvvBzA+EveAcEN/bwa77Gc5eXKAME4SPcAz2VuwOmgc9evOJKWhks+Kpiv1uI+Z886hLGa+iz0yZbGv2ZKEgU9mEL7/pv6Ps3Y9RQQbVs9O9sI6Djnoi9LobYWjHOzSvTy2WYNkfuJCwkyLAWt6IrmQIDAQAB";
        String cryptPrivateKeyStr = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDhiulmJKfy9TeMzIcCQGf6zBj8YOdgq1LJayfbEdeYTE0MeKoUAhaKeiTcnk9X8rYhW0DLAbSy36SY8/lbxvQXdR4fB9CPxeep2GZZS9RudKqNMS72Tx+GJALD2W0GLuLYz4f1JBIdMwyHlSAiqQN5XfIH1CQSa6i2HZXYuzc0WPIatk6O+DAAt9cHN6Y0+W+8HMD4S94BwQ39vBrvsZzl5coAwThI9wDPZW7A6aBz1684kpaGSz4qmK/W4j5nzzqEsZr6LPTJlsa/ZkoSBT2YQvv+m/o+zdj1FBBtWz072wjoOOeiL0uhthaMc7NK9PLZZg2R+4kLCTIsBa3oiuZAgMBAAECggEAcUqtZiMaidMGNbBbxZJsd9gbArwS5yadJ1H4dArNHxsRSyh9WQY3a5MV54x/qsJM9F1aHZVa+1CWYnrfpX4js0RftLkWI/Y83kjH5V4neHpcYIi7viPN28BEs4I71PpI8jQ2Wyy6a+9PxQwjNeY2klHK7/MpbmBJsbRLm9J2vW3UKHIsmK8enngMJQei/dWXyT0wAiR+vSk6NrZ57T067cyTP9I3c8XsP7AakPqY9R/sJ7ebkcw9dEat6EC/hcZyrj158YbWe1zvKn51KTYYS0FHYf8a8XukzYkM8WHcPAD4Od+PNP0ERvoYEV1FVx7ScFV2/itU933B4CoO+zJGkQKBgQC4/ftqErQ40piT0VCDb3uHctUhZvebyEsqZF2pG1Isv/nAIrid+IjBnfg2E25gzMTf6M0a3X5W+rLtPOji+fbM4F9/c94jeuQe2UTY5Th/Hge6A1WyOkcIPyEFHQWSO2R4lE+CRqhrGzQE0PC6VUja3g5Gsvh7WlyU+h61yYcCuwKBgQC2AjihcRYI1qgMLTyaHcmtXy9SH7ZwLio2l1rgX/kyh5g/jkD9sQ6dNX2PGiVKImrBQZc/XtIqB3PwbSPoH4UQXjqPL53WObuhWYNZuNMaIQIA6uQPnXEO8NTpnYlRtcz1lBuOWCpcCLOIAazMCUpgoiLO5Lnbgwl5brOzuvc3uwKBgAWtLXjMyCDyPWJTyu+tjRHT8TxMJ47tL3lGiSU2RGhBH83Igve995xRV5t5LyrjPatNPnXzk/vhR3pGIzNLzkntHKERkiBule9oZVdueTRVXJFVptyIM0PKhB3fDRu2nNeiXWo29tYKiKcZLjnZghnNNIzw0vUeCbc8kZKsc2sfAoGAeKhkTnIff2B+KwRaJnkU18BAKJspYJjaFzKjZhd5pgvJ3Gg7oXKdE48AW7ujeM/0IYWP9MyxWndyXrKbmSg8GCu/emjAo+HGByLGNtaV675yC5Wr9sYSyyrQmsZ64BY7d5nl4qmD+1sxWTpzCRgnnIQfuzRaGbF2uG+w5bdOaD0CgYB9NwHjlHjgJ8h2E2UGdgYkzK3W+kJbnlQAdV+Uv+K5IyuCIre7BLxzutTAY5sKJNnNMGha8jcJVJbKHvE+PFdu9ckjT1rJU0YMlbiAkGqHUMh9+68Nbkhn3DIO0PCkrkWwAzuk0GQb05Ka7kjy7FgyjD5HX4PhgUIeWRUF+NfAvQ==";

        KeyFactory keyFactory = KeyFactory.getInstance(SignAndEncryptConstant.ENCRYPT_DECRYPT_ALGORITHM);
        // 签名
        PublicKey signPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(signPublicKeyStr)));
        PrivateKey signPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(signPrivateKeyStr)));
        // 参数
        PublicKey cryptPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decodeFromString(cryptPublicKeyStr)));
        PrivateKey cryptPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decodeFromString(cryptPrivateKeyStr)));

        Assert.notNull(signPublicKey, "签名公钥不能为NULL");
        Assert.notNull(signPrivateKey, "签名私钥不能为NULL");
        Assert.notNull(cryptPublicKey, "参数公钥不能为NULL");
        Assert.notNull(cryptPrivateKey, "参数公钥不能为NULL");

        Mockito.when(signAndEncryptInitConfig.getSignPublicKey()).thenReturn(signPublicKey);
        Mockito.when(signAndEncryptInitConfig.getSignPrivateKey()).thenReturn(signPrivateKey);
        Mockito.when(signAndEncryptInitConfig.getCryptPublicKey()).thenReturn(cryptPublicKey);
        Mockito.when(signAndEncryptInitConfig.getCryptPrivateKey()).thenReturn(cryptPrivateKey);
    }


    @Test
    public void signAndEncryptTest() {
        // 参数
        String param = new JSONObject().fluentPut("name", "张三").toJSONString();
        // 加密
        String data = signAndEncryptUtils.encrypt(param);
        // 加签
        String sign = signAndEncryptUtils.sign(data);

        // 判断签名是否正确
        assertTrue(signAndEncryptUtils.verifySign(data, sign));
        // 解密
        String decryptData = signAndEncryptUtils.decrypt(data);
        assertEquals(param, decryptData);
    }
}