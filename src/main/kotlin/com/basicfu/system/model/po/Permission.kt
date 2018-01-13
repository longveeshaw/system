package com.basicfu.system.model.po

import javax.persistence.*

class Permission {
    /**
     * 权限ID
     */
    /**
     * 获取权限ID
     *
     * @return id - 权限ID
     */
    /**
     * 设置权限ID
     *
     * @param id 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /**
     * 权限名
     */
    /**
     * 获取权限名
     *
     * @return name - 权限名
     */
    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    var name: String? = null
}