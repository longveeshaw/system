package com.basicfu.system.common.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommonMapper<T> {

    @InsertProvider(type = CommonProvider.class, method = "dynamicSQL")
    int insertListNoUsePrimaryKey(List<T> list);

    @UpdateProvider(type = CommonProvider.class, method = "dynamicSQL")
    int updateCustomSql(@Param("sql")String sql);

}
