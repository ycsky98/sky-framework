package org.sky.framework.config.mybatis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.sky.framework.config.mybatis.annotation.Data;
import org.sky.framework.config.mybatis.source.DataSourceLocal;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yangcong
 *
 * aop切多数据源
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 切入方法更换数据源
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(org.sky.framework.config.mybatis.annotation.Data)")
    public Object around(ProceedingJoinPoint joinPoint)throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Data data = method.getAnnotation(Data.class);
        System.out.printf("当前数据源    ====>>>> %s \n", data.value());
        if(data != null){
            String value = data.value();
            //切换成指定的数据源
            DataSourceLocal.setDataSource(value);
        }
        try {
            return joinPoint.proceed();
        }finally {
            //操作完成，移除指定数据源，还原默认数据源
            DataSourceLocal.removeDataSource();
        }
    }

}
