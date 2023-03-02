package org.sky.framework.config.web.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.sky.framework.config.web.annotation.RepeatSubmit;
import org.sky.framework.config.web.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yangcong
 *
 * 配置防重复提交
 */
@Component
@Aspect
@Order(4)//最后做防重复提交
public class NoRepeatSubmitAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TokenProperties tokenProperties;

    @Around(value = "@annotation(org.sky.framework.config.web.annotation.RepeatSubmit)")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        /**
         * 获取请求信息
         */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        // 获取执行方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        //获取防重复提交注解
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);

        // 获取token以及方法标记，生成redisKey和redisValue
        String token = request.getHeader(tokenProperties.getHeader());

        String url = request.getRequestURI();

        /**
         *  通过前缀 + url + token + 函数参数签名 来生成redis上的 key
         *
         */
        String redisKey = tokenProperties.getPREVENT_DUPLICATION_PREFIX()
                .concat(url)
                .concat(token)
                .concat(getMethodSign(method, joinPoint.getArgs()));

        // 这个值只是为了标记，不重要
        String redisValue = redisKey.concat(annotation.value()).concat("submit duplication");

        if (!redisTemplate.hasKey(redisKey)) {
            // 设置防重复操作限时标记(前置通知)
            redisTemplate.opsForValue()
                    .set(redisKey, redisValue, annotation.expireSeconds(), TimeUnit.SECONDS);
            try {
                //正常执行方法并返回
                //ProceedingJoinPoint类型参数可以决定是否执行目标方法，
                // 且环绕通知必须要有返回值，返回值即为目标方法的返回值
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                //确保方法执行异常实时释放限时标记(异常后置通知)
                redisTemplate.delete(redisKey);
                throw new RuntimeException(throwable);
            }
        } else {
            // 重复提交了抛出异常，如果是在项目中，根据具体情况处理。
            throw new RuntimeException("请勿重复提交");
        }
    }

    /**
     * 生成方法标记：采用数字签名算法SHA1对方法签名字符串加签
     *
     * @param method
     * @param args
     * @return
     */
    private String getMethodSign(Method method, Object... args) throws JsonProcessingException{
        StringBuilder sb = new StringBuilder(method.toString());
        for (Object arg : args) {
            sb.append(toString(arg));
        }
        return DigestUtils.sha1DigestAsHex(sb.toString());
    }

    private String toString(Object arg) throws JsonProcessingException {
        if (Objects.isNull(arg)) {
            return "null";
        }
        if (arg instanceof Number) {
            return arg.toString();
        }
        return new ObjectMapper().writeValueAsString(arg);
    }
}
