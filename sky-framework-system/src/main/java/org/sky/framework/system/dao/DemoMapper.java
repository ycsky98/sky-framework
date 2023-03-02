package org.sky.framework.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.sky.framework.api.common.domain.base.BaseEntity;
import org.sky.framework.config.mybatis.annotation.Data;

import java.util.List;
import java.util.Map;

@Mapper
public interface DemoMapper {

    public List<BaseEntity> selectMaster();

    @Data("slaveDataSource")
    public List<Map<String, Object>> selectSlave();
}
