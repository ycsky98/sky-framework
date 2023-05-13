package org.sky.framework.config.mybatis.properties;

import org.sky.framework.config.yaml.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.master")
@PropertySource(value = "classpath:application.yaml", encoding = "UTF-8", factory = YamlPropertySourceFactory.class)
public class MasterDataSourceProperties {

    private String url;
    private String driver;
    private String username;
    private String password;
    private Long connectionTimeout;
    private Long idleTimeout;
    private Long maxLifeTime;
    private Long maximumPoolSize;
    private Long minimumIdle;
    private String poolName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Long getMaxLifeTime() {
        return maxLifeTime;
    }

    public void setMaxLifeTime(Long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public Long getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Long maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(Long minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }
}
