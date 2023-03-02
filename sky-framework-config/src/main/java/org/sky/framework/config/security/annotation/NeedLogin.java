package org.sky.framework.config.security.annotation;

import java.lang.annotation.*;

/**
 * @author yangcong
 *
 * 需要登陆(用于控制器配置)
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
}
