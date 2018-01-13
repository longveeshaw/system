package com.basicfu.system.model.po

import javax.persistence.*

@Table(name = "user_role")
class UserRole {
    /**
     * 用户ID
     */
    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    @Id
    @Column(name = "user_id")
    var userId: Long? = null

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
    @Column(name = "role_id")
    var roleId: Long? = null
}