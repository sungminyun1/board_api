package com.springBoard.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@Aspect
public class LoginCheckAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @Around(value = "@annotation(loginCheck)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        log.info("here i am login check {}",joinPoint);

        for(Object obj : joinPoint.getArgs()){
            log.info("here i am login check2 {}",obj);
        }

        return joinPoint.proceed();
    }
}
