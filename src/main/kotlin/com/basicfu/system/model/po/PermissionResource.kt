package com.basicfu.system.model.po

import javax.persistence.*

@Table(name = "permission_resource")
class PermissionResource {
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
    @Id
    @Column(name = "permission_id")
    var permissionId: Long? = null

    /**
     * 资源ID
     */
    /**
     * 获取资源ID
     *
     * @return resource_id - 资源ID
     */
    /**
     * 设置资源ID
     *
     * @param resourceId 资源ID
     */
    @Column(name = "resource_id")
    var resourceId: Long? = null
}