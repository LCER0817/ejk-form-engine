package com.ns.common.aop;

import com.ns.common.util.exception.sys.NSException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 统一事务切面
 * Created by liqiuwei on 2017/6/30.
 */
@Aspect
@Component
public class AopNSTransaction {
    @Resource
    private JpaTransactionManager transactionManager;

    /**
     * 增加切入点
     */
    @Pointcut("execution(* com.ns..biz..getAndLock*(..))")
    public void getPointCut() {
    }

    /**
     * 增加切入点
     */
    @Pointcut("execution(* com.ns..biz..add*(..)) " +
            "|| execution(* com.ns..biz..save*(..)) " +
            "|| execution(* com.ns..biz..create*(..))")
    public void createPointCut() {
    }

    /**
     * 更新切入点
     */
    @Pointcut("execution(* com.ns..biz..modify*(..)) " +
            "|| execution(* com.ns..biz..update*(..))")
    public void updatePointCut() {
    }

    /**
     * 删除切入点
     */
    @Pointcut("execution(* com.ns..biz..remove*(..)) " +
            "|| execution(* com.ns..biz..delete*(..))")
    public void deletePointCut() {
    }

    /**
     * 带有事务注解的方法
     */
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalAnnotation() {

    }

    /**
     * 带有事务注解的方法不进行拦截
     *
     * @param joinPoint
     * @return
     * @throws NSException
     */
    @Around("(AopNSTransaction.getPointCut() " +
            "|| AopNSTransaction.createPointCut() " +
            "|| AopNSTransaction.deletePointCut() " +
            "|| AopNSTransaction.updatePointCut()) " +
            "&& !AopNSTransaction.transactionalAnnotation()")
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        return AopNSTransactionHandler.handle(joinPoint, transactionManager);
    }

}
