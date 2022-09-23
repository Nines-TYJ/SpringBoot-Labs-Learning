package com.nines.framework.sign.aspect;

import com.nines.framework.sign.config.SignAndEncryptInitConfig;
import com.nines.framework.sign.config.SignAndEncryptSecretKeyProperties;
import com.nines.framework.sign.constant.SignAndEncryptConstant;
import com.nines.framework.sign.utils.SignAndEncryptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author tanyujie
 * @classname SignAndEncryptAspect
 * @description 加解密切面 请求收到时
 * @date 2022/8/18 11:32
 * @since 1.0
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnClass({SignAndEncryptInitConfig.class, SignAndEncryptConstant.class, SignAndEncryptUtils.class})
@ConditionalOnProperty(prefix = "sign", name = "enable", havingValue = "true")
public class SignAndEncryptAspect {

    //@Pointcut("execution(* com.*.*.*.*)")
    //public void pointCut() {}
    //
    //@Around("pointCut()")
    //public Object signAndEncryptAround(ProceedingJoinPoint point) {
    //
    //}

}
