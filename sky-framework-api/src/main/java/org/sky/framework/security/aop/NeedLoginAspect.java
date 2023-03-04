package org.sky.framework.security.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sky.framework.config.exception.business.BusinessException;
import org.sky.framework.config.web.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author yangcong
 *
 * 配置登陆校验注解
 */
@Aspect
@Component
@Order(3)//优先于RepeatSubmit切面
public class NeedLoginAspect {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around(value = "@annotation(org.sky.framework.security.annotation.NeedLogin)")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        /**
         * 获取请求信息
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        String token = attributes.getRequest().getHeader(tokenProperties.getHeader());

        //检测是否登陆
        if(!redisTemplate.hasKey(token)){
            throw new BusinessException("用户未登陆!");
        }

        //执行controller
        return joinPoint.proceed();
    }
}
