package org.sky.framework.config.mybatis.source;

/**
 * @author yangcong
 *
 * 动态数据源
 */
public class DataSourceLocal {

    private static final ThreadLocal<String> dataSource = new ThreadLocal<String>();

    /**
     * 设置数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){
        DataSourceLocal.dataSource.set(dataSource);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource(){
        return DataSourceLocal.dataSource.get();
    }

    /**
     * 删除数据源
     */
    public static void removeDataSource(){
        DataSourceLocal.dataSource.remove();
    }
}
