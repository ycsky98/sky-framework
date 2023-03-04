package org.sky.framework.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yangcong
 *
 * 配置登陆校验注解
 */
@Aspect
@Component
@Order(3)//优先于RepeatSubmit切面
public class NeedLoginAspect {

    @Around(value = "@annotation(org.sky.framework.security.annotation.NeedLogin)")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        //检测是否登陆

        //执行controller
        return joinPoint.proceed();
    }
}
