package com.basicfu.system.common.datasource

/**
 * @author fuliang
 * @date 2018/1/18
 */
class DataSourceContextHolder {
    enum class DataSourceType {
        MASTER,
        READ
    }

    companion object {
        private val contextHolder = ThreadLocal<DataSourceType>()
        fun read() {
            contextHolder.set(DataSourceType.READ)
        }

        fun write() {
            contextHolder.set(DataSourceType.MASTER)
        }

        fun get(): String {
            return contextHolder.get().name
        }
    }
}
