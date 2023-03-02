## <center> SKY-FRAMEWORK

### <center> Author yangcong
### <center> 一款基于SpringBoot的快速脚手架

### <center> boot+security+mybatis+redis

---- 

- 依赖配置介绍<p>
--- 
    <modules>
        <module>sky-framework-common</module> <!-- 功能包,公共包 -->
        <module>sky-framework-config</module> <!-- 框架配置包 -->
        <module>sky-framework-api</module> <!-- 对外api -->
        <module>sky-framework-system</module> <!-- 系统功能包 -->
    </modules>

- application.yaml 配置介绍<p>
---
    spring:
        application:
        name: skyBootFramework
        datasource:
            ## 主库
            master:
            url: jdbc:mysql://localhost:3306/xxxx?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
            driver: com.mysql.cj.jdbc.Driver
            username: root
            password: xxxx
            connectionTimeout: 30000
            idleTimeout: 600000
            maxLifeTime: 1800000
            maximumPoolSize: 100
            minimumIdle: 10
            poolName: masterDataSource
        ## 从库
        slave:
            ## 是否开启从库
            enabled: false
            url: jdbc:mysql://localhost:3306/xxxxx?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8
            driver: com.mysql.cj.jdbc.Driver
            username: root
            password: xxxx
            connectionTimeout: 30000
            idleTimeout: 600000
            maxLifeTime: 1800000
            maximumPoolSize: 100
            minimumIdle: 10
            poolName: slaveDataSource
        redis:
            # Redis服务器地址
            host: localhost
            # Redis服务器端口号
            port: 6379
            # 使用的数据库索引，默认是0
            database: 0
            # 连接超时时间
            timeout: 1800000
            # 设置密码
            password: 123456
            lettuce:
            pool:
            # 最大阻塞等待时间，负数表示没有限制
            max-wait: -1
            # 连接池中的最大空闲连接
            max-idle: 5
            # 连接池中的最小空闲连接
            min-idle: 0
            # 连接池中最大连接数，负数表示没有限制
            max-active: 20
        server:
            servlet:
                encoding:
                    charset: utf-8
            port: 8080
            logging:
                config: classpath:log4j2.xml
        
        login:
            ## token-header
            header: TOKEN
            ## redis放重复前缀(用于处理防重复提交)
            PREVENT_DUPLICATION_PREFIX: PREVENT_DUPLICATION_PREFIX

- 如何设置主从数据切换<p>
---
    在dao或者service中配置@Data注解指定数据源












