package com.basicfu.system.common

import tk.mybatis.mapper.common.IdsMapper
import tk.mybatis.mapper.common.Mapper
import tk.mybatis.mapper.common.MySqlMapper

/**
 * @author fuliang
 * @date 17/10/26
 */
interface CustomMapper<T> : Mapper<T>, MySqlMapper<T>,IdsMapper<T>,com.basicfu.system.common.mapper.CommonMapper<T>//TODO
//FIXME 特别注意，该接口不能被扫描到，否则会出错
