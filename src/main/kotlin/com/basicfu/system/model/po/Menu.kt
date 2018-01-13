package com.basicfu.system.model.po

import javax.persistence.*

class Menu {
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
     * 父级ID
     */
    /**
     * 获取父级ID
     *
     * @return parent_id - 父级ID
     */
    /**
     * 设置父级ID
     *
     * @param pid 父级ID
     */
    var pid: Long? = null

    /**
     * 菜单名
     */
    /**
     * 获取菜单名
     *
     * @return name - 菜单名
     */
    /**
     * 设置菜单名
     *
     * @param name 菜单名
     */
    var name: String? = null

    /**
     * 菜单URL
     */
    /**
     * 获取菜单URL
     *
     * @return url - 菜单URL
     */
    /**
     * 设置菜单URL
     *
     * @param url 菜单URL
     */
    var url: String? = null

    /**
     * 菜单顺序
     */
    /**
     * 获取菜单顺序
     *
     * @return sort - 菜单顺序
     */
    /**
     * 设置菜单顺序
     *
     * @param sort 菜单顺序
     */
    var sort: Int? = null

    /**
     * 菜单图标
     */
    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    var icon: String? = null
}