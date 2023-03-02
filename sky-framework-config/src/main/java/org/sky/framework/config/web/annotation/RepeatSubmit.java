package org.sky.framework.config.web.annotation;

import java.lang.annotation.*;

/**
 * @author yangcong
 *
 * 防重复提交
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {

    /**
     * 防重复操作限时标记数值(存储redis限时标记数值)
     */
    String value() default "value" ;

    /**
     * 防重复操作过期时间(借助redis实现限时控制)
     */
    long expireSeconds() default 1;

}
