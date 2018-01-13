package com.basicfu.system.model.po

import java.io.Serializable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

class Resource:Serializable{
    /**
     * 资源ID
     */
    /**
     * 获取资源ID
     *
     * @return id - 资源ID
     */
    /**
     * 设置资源ID
     *
     * @param id 资源ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /**
     * 资源URL
     */
    /**
     * 获取资源URL
     *
     * @return url - 资源URL
     */
    /**
     * 设置资源URL
     *
     * @param url 资源URL
     */
    var url: String? = null

    /**
     * 资源名
     */
    /**
     * 获取资源名
     *
     * @return name - 资源名
     */
    /**
     * 设置资源名
     *
     * @param name 资源名
     */
    var name: String? = null
}