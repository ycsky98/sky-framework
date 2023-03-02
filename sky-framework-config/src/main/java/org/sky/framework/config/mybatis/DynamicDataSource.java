package org.sky.framework.config.mybatis;

import org.sky.framework.config.mybatis.source.DataSourceLocal;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author yangcong
 *
 * 扩展 Spring 的 AbstractRoutingDataSource 抽象类，重写 determineCurrentLookupKey 方法
 * 动态数据源
 * determineCurrentLookupKey() 方法决定使用哪个数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceLocal.getDataSource();
    }
}
