package com.basicfu.system.model.po

import javax.persistence.*

class Role {
    /**
     * 主键
     */
    /**
     * 获取主键
     *
     * @return id - 主键
     */
    /**
     * 设置主键
     *
     * @param id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /**
     * 角色名
     */
    /**
     * 获取角色名
     *
     * @return name - 角色名
     */
    /**
     * 设置角色名
     *
     * @param name 角色名
     */
    var name: String? = null
}