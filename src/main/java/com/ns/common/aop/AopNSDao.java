package com.ns.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by caoxuezhu01 on 14-9-15.
 */
@Aspect
@Component
public class AopNSDao {

    @Around("execution(* com.ns..dao..*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        return AopNSDaoHandler.handle(joinPoint);
    }
}
