package com.ns.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by cxz on 6/19/14.
 */
@Aspect
@Component
public class AopNSJsonAction {

    @Around("execution(* com.ns..action..*(..))")
    private Object handle(ProceedingJoinPoint joinPoint) {
        return AopNSJsonHandler.handle(joinPoint);
    }
}
