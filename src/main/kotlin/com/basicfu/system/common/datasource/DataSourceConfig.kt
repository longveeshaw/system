package com.basicfu.system.common.datasource

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder
import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource


/**
 * @author fuliang
 * @date 17/10/26
 */
@Configuration
@MapperScan(basePackages = ["com.basicfu.system.mapper"])
class DataSourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    fun masterDataSource(): DataSource {
        return DruidDataSourceBuilder.create().build()
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.read1")
//    fun read1DataSource(): DataSource {
//        return DruidDataSourceBuilder.create().build()
//    }

    /**
     * 数据源
     */
    @Bean
    fun dynamicDataSource(): DataSource {
        val targetDataSources = HashMap<Any, Any>()
        val masterDataSource = masterDataSource()
        targetDataSources.put(DataSourceContextHolder.DataSourceType.MASTER.name+"0", masterDataSource)
//        targetDataSources.put(DataSourceContextHolder.DataSourceType.READ.name+"0", read1DataSource())
        val dataSource = DynamicDataSource(1,2)
        dataSource.setTargetDataSources(targetDataSources)
        dataSource.setDefaultTargetDataSource(masterDataSource)
        return masterDataSource
    }

    /**
     * sqlSessionFactor
     */
    @Bean
    fun sqlSessionFactoryBean(dataSource: DataSource): SqlSessionFactoryBean {
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(dataSource)
        sqlSessionFactoryBean.setMapperLocations(PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"))
        return sqlSessionFactoryBean
    }

    /**
     * 事物
     */
    @Bean
    fun transactionManager(dataSource: DataSource): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    /**
     * druid监控
     */
    @Bean
    fun druidServlet(): ServletRegistrationBean {
        val servletRegistrationBean = ServletRegistrationBean()
        servletRegistrationBean.setServlet(StatViewServlet())
        servletRegistrationBean.addUrlMappings("/druid/*")
        return servletRegistrationBean
    }

    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean {
        val filterRegistrationBean = FilterRegistrationBean()
        filterRegistrationBean.filter = WebStatFilter()
        filterRegistrationBean.addUrlPatterns("/*")
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*")
        return filterRegistrationBean
    }

}