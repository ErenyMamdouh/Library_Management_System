package com.librarymanagementsystem.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.librarymanagementsystem.service.*.*(..))")
    public void serviceLayer(){}

    @Before("serviceLayer()")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        log.info("Method called: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint ,Throwable ex){
        log.error("Exception in method: "+joinPoint.getSignature().getName()+"with message"+ex.getMessage());

    }

    @Around("serviceLayer()")
    public Object performanceMatrice(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long timeTaken=System.currentTimeMillis() - startTime;
        log.info("Method "+joinPoint.getSignature().getName()+" executed in " +timeTaken + "ms");

        return result;
    }













}
