package com.basicfu.system.common.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CommonMapper<T> {

    @InsertProvider(type = CommonProvider.class, method = "dynamicSQL")
    int insertListNoUsePrimaryKey(List<T> list);

}
