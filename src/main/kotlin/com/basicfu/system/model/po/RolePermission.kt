package com.basicfu.system.model.po

import javax.persistence.*

@Table(name = "role_permission")
class RolePermission {
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
     * 权限ID
     */
    /**
     * 获取权限ID
     *
     * @return permission_id - 权限ID
     */
    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    @Column(name = "permission_id")
    var permissionId: Long? = null
}