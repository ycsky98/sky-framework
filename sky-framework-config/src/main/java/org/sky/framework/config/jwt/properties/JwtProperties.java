package org.sky.framework.config.jwt.properties;

import org.sky.framework.config.yaml.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yangcong
 *
 * jwt属性配置
 */
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
@PropertySource(value = "classpath:application.yaml", encoding = "UTF-8", factory = YamlPropertySourceFactory.class)
public class JwtProperties {

    /**
     * 加密密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private Long expire;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }
}
