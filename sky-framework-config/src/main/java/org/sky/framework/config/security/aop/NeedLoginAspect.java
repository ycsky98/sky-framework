package org.sky.framework.config.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)//优先于RepeatSubmit切面
public class NeedLoginAspect {

    @Around(value = "@annotation(org.sky.framework.config.security.annotation.NeedLogin)")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        return joinPoint.proceed();
    }
}
