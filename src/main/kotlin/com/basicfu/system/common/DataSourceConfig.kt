package com.basicfu.system.common

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import java.io.IOException
import javax.sql.DataSource

/**
 * @author fuliang
 * @date 17/10/26
 */
@Configuration
@MapperScan(basePackages = arrayOf("com.basicfu.system.mapper"))
class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    fun dataSourceOne(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

    @Bean
    @Throws(IOException::class, ClassNotFoundException::class)
    fun sqlSessionFactoryBean(): SqlSessionFactoryBean {
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(dataSourceOne())
        sqlSessionFactoryBean.setMapperLocations(PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"))
        return sqlSessionFactoryBean
    }

    @Bean
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSourceOne())
    }
}