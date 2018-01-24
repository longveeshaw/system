package com.basicfu.system.common.datasource

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

/**
 * @author fuliang
 * @date 2018/1/18
 */
@Aspect
@Component
class DataSourceAop {
    @Before("execution(* com.basicfu.system.mapper..*.select*(..)) || execution(* com.basicfu.system.mapper..*.count*(..))")
    fun read() {
        DataSourceContextHolder.read()
    }

    @Before("execution(* com.basicfu.system.mapper..*.insert*(..)) || execution(* com.basicfu.system.mapper..*.update*(..)) || execution(* com.basicfu.system.mapper..*.delete*(..))")
    fun write() {
        DataSourceContextHolder.write()
    }
}