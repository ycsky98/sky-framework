package org.sky.framework.config.web.properties;

import org.sky.framework.config.yaml.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "login")
@PropertySource(value = "classpath:application.yaml", encoding = "UTF-8", factory = YamlPropertySourceFactory.class)
public class TokenProperties {

    private String header;

    private String PREVENT_DUPLICATION_PREFIX;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPREVENT_DUPLICATION_PREFIX() {
        return PREVENT_DUPLICATION_PREFIX;
    }

    public void setPREVENT_DUPLICATION_PREFIX(String PREVENT_DUPLICATION_PREFIX) {
        this.PREVENT_DUPLICATION_PREFIX = PREVENT_DUPLICATION_PREFIX;
    }
}
