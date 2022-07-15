package com.lynn.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
@Component
// AOP通知测试类
public class TestAspect {

    private static final Logger log = LogManager.getLogger(TestAspect.class);

    // 声明切入点
    @Pointcut("execution(* com.lynn.service.*.*(..))")
    public void cut1(){}

    // 声明前置通知
    @Before("cut1()")
    public void execution(JoinPoint joinPoint) {
        log.info("{}方法已执行（前置通知）",joinPoint.getSignature().getName());
    }

    // 声明前置通知
    @Before("cut1() && args(java.io.Serializable)")
    public void args(JoinPoint joinPoint) {
        log.info("{}方法已执行（前置通知）",joinPoint.getSignature().getName());
    }
}
