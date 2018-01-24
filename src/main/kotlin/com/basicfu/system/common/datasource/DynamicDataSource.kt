package com.basicfu.system.common.datasource

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.util.concurrent.atomic.AtomicLong

/**
 * @author fuliang
 * @date 2018/1/18
 */
class DynamicDataSource(private val writeDataSourceNumber:Int, private val readDataSourceNumber:Int):AbstractRoutingDataSource() {
    private val readCount = AtomicLong(0)
    private val writeCount = AtomicLong(0)

    override fun determineCurrentLookupKey():Any {
        //简单负载均衡-随机分发到一个读中，当超出最大值时未知发生什么情况，后续待改进
        return if (DataSourceContextHolder.get() == DataSourceContextHolder.DataSourceType.MASTER.name){
            val a="${DataSourceContextHolder.DataSourceType.MASTER.name}${writeCount.getAndAdd(1) % writeDataSourceNumber}"
            println(a)
            return a
        }else{
            val b="${DataSourceContextHolder.DataSourceType.READ.name}${readCount.getAndAdd(1) % readDataSourceNumber}"
            println(b)
            return b
        }
    }
}