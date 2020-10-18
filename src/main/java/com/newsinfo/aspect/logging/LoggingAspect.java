package com.newsinfo.aspect.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("@annotation(Logged)")
    public void logBeforeExecution(JoinPoint joinPoint) {
        log.info("before execution of - [{}]", joinPoint.getSignature().getName());
    }

    @AfterReturning("@annotation(Logged)")
    public void logAfterSuccessExecution(JoinPoint joinPoint) {
        log.info("after successful execution of - [{}]", joinPoint.getSignature().getName());
    }

    @AfterThrowing("@annotation(Logged)")
    public void logAfterException(JoinPoint joinPoint) {
        log.info("after throwing exception on - [{}]", joinPoint.getSignature().getName());
    }

    @After("@annotation(Logged)")
    public void logAfterAll(JoinPoint joinPoint) {
        log.info("after execution on - [{}]", joinPoint.getSignature().getName());
    }

    @Pointcut("execution(* org.springframework.data.repository.Repository+.*(..))")
    public void repositoryMethod() {
    }

    @Around("repositoryMethod()")
    public Object logJpaRepositoryTransactions(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();
        log.info("JVM memory in use = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
        Object output = null;
        try {
            output = pjp.proceed();
        } catch (Throwable e) {
            log.info(e.getMessage(), e);
        }
        long elapsedTime = System.currentTimeMillis() - start;
        log.info(pjp.getTarget() + "." + pjp.getSignature() + ": Execution time: " + elapsedTime + " ms. (" + elapsedTime / 60000 + " minutes)");
        return output;
    }
}
