package org.sky.framework.config.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.sky.framework.config.mybatis.properties.MasterDataSourceProperties;
import org.sky.framework.config.mybatis.properties.SlaveDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangcong
 *
 * mybatis 配置
 */
@Configuration
public class MybatisBeanConfig {

    @Autowired
    private MasterDataSourceProperties masterDataSourceProperties;

    @Autowired
    private SlaveDataSourceProperties slaveDataSourceProperties;

    /**
     * 主数据源
     * @return
     */
    @Bean("masterDataSource")
    @Primary
    public HikariDataSource masterDataSource(){
        HikariDataSource masterDataSource = new HikariDataSource();
        masterDataSource.setJdbcUrl(masterDataSourceProperties.getUrl());
        masterDataSource.setDriverClassName(masterDataSourceProperties.getDriver());
        masterDataSource.setUsername(masterDataSourceProperties.getUsername());
        masterDataSource.setPassword(masterDataSourceProperties.getPassword());
        //连接超时
        masterDataSource.setConnectionTimeout(masterDataSourceProperties.getConnectionTimeout());
        //生命周期最大时长,否则释放连接
        masterDataSource.setIdleTimeout(masterDataSourceProperties.getIdleTimeout());
        masterDataSource.setMaxLifetime(masterDataSourceProperties.getMaxLifeTime());
        //最大连接数量
        masterDataSource.setMaximumPoolSize(masterDataSourceProperties.getMaximumPoolSize().intValue());
        //最小连接数
        masterDataSource.setMinimumIdle(masterDataSourceProperties.getMinimumIdle().intValue());
        //连接池名称
        masterDataSource.setPoolName(masterDataSourceProperties.getPoolName());

        return masterDataSource;
    }

    @Bean("slaveDataSource")
    public HikariDataSource slaveDataSource(){
        HikariDataSource slaveDataSource = new HikariDataSource();
        //如果关闭
        if (!this.slaveDataSourceProperties.getEnabled()){
            return slaveDataSource;
        }
        slaveDataSource.setJdbcUrl(slaveDataSourceProperties.getUrl());
        slaveDataSource.setDriverClassName(slaveDataSourceProperties.getDriver());
        slaveDataSource.setUsername(slaveDataSourceProperties.getUsername());
        slaveDataSource.setPassword(slaveDataSourceProperties.getPassword());
        //连接超时
        slaveDataSource.setConnectionTimeout(slaveDataSourceProperties.getConnectionTimeout());
        //生命周期最大时长,否则释放连接
        slaveDataSource.setIdleTimeout(slaveDataSourceProperties.getIdleTimeout());
        slaveDataSource.setMaxLifetime(slaveDataSourceProperties.getMaxLifeTime());
        //最大连接数量
        slaveDataSource.setMaximumPoolSize(slaveDataSourceProperties.getMaximumPoolSize().intValue());
        //最小连接数
        slaveDataSource.setMinimumIdle(slaveDataSourceProperties.getMinimumIdle().intValue());
        //连接池名称
        slaveDataSource.setPoolName(slaveDataSourceProperties.getPoolName());

        return slaveDataSource;
    }

    /**
     * 多数据源配置
     */
    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("masterDataSource")HikariDataSource masterDataSource,
                                               @Qualifier("slaveDataSource")HikariDataSource slaveDataSource){
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put("masterDataSource", masterDataSource);

        if (this.slaveDataSourceProperties.getEnabled()){
            targetDataSource.put("slaveDataSource", slaveDataSource);
        }

        //设置多数据源和默认数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //多数据源
        dynamicDataSource.setTargetDataSources(targetDataSource);
        //默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);

        return dynamicDataSource;
    }

    /**
     * sqlSessionFactory会话工厂(配置进多数据源)
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource")HikariDataSource masterDataSource,
                                               @Qualifier("slaveDataSource")HikariDataSource slaveDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置多数据源
        sqlSessionFactoryBean.setDataSource(dynamicDataSource(masterDataSource, slaveDataSource));
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**.xml")
        );
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置主数据库事务管理器
     *
     * @param masterDataSource
     */
    @Bean
    @Primary
    public DataSourceTransactionManager masterDataSourceTransactionManager(
            @Qualifier("masterDataSource")HikariDataSource masterDataSource){
        return new DataSourceTransactionManager(masterDataSource);
    }

    /**
     * 配置从库事务管理器
     *
     * @param slaveDataSource
     */
    @Bean
    public DataSourceTransactionManager slaveDataSourceTransactionManager(
            @Qualifier("slaveDataSource")HikariDataSource slaveDataSource){
        return new DataSourceTransactionManager(slaveDataSource);
    }

    /**
     * 主数据源手动事务
     *
     * @param masterDataSourceTransactionManager
     * @return
     */
    @Bean
    @Primary
    public TransactionTemplate masterTransactionTemplate(
            @Qualifier("masterDataSourceTransactionManager") DataSourceTransactionManager masterDataSourceTransactionManager){
        TransactionTemplate master = new TransactionTemplate();
        master.setTransactionManager(masterDataSourceTransactionManager);

        return master;
    }
    
    /**
     * 从库数据源手动事务
     * @param slaveDataSourceTransactionManager
     * @return
     */
    @Bean
    public TransactionTemplate slaveTransactionTemplate(
            @Qualifier("slaveDataSourceTransactionManager") DataSourceTransactionManager slaveDataSourceTransactionManager){
        TransactionTemplate slave = new TransactionTemplate();
        slave.setTransactionManager(slaveDataSourceTransactionManager);

        return slave;
    }
}
