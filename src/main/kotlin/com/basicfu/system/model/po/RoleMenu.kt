package com.basicfu.system.model.po

import javax.persistence.*

@Table(name = "role_menu")
class RoleMenu {
    /**
     * 角色ID
     */
    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    @Id
    @Column(name = "role_id")
    var roleId: Long? = null

    /**
     * 菜单ID
     */
    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    @Column(name = "menu_id")
    var menuId: Long? = null
}