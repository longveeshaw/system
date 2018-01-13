package com.basicfu.system.common

import com.basicfu.common.util.RedisUtil
import com.basicfu.system.mapper.PermissionResourceMapper
import com.basicfu.system.mapper.ResourceMapper
import com.basicfu.system.mapper.RoleMapper
import com.basicfu.system.mapper.RolePermissionMapper
import com.basicfu.system.model.po.Role
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * @author fuliang
 * @date 2017/11/19
 */
@Component
class InitConfig : CommandLineRunner {
    @Autowired lateinit var resourceMapper: ResourceMapper
    @Autowired lateinit var roleMapper: RoleMapper
    @Autowired lateinit var rolePermissionMapper: RolePermissionMapper
    @Autowired lateinit var permissionResourceMapper: PermissionResourceMapper
    @Autowired lateinit var redisTemplate: RedisTemplate<Any, Any>
    override fun run(vararg strings: String) {
        RedisUtil.init(redisTemplate)
        initResource()
        initNotLoginToken()
    }

    /**
     * 初始化访客拥有的资源id
     */
    fun initNotLoginToken(){
        val role= Role()
        role.name="访客"
        val guestRole = roleMapper.selectOne(role)
        if(guestRole!=null) {
            val pids = rolePermissionMapper.selectByIds(guestRole.id.toString()).mapNotNull { it.permissionId }
            if(pids.isNotEmpty()){
                val resourceIds = permissionResourceMapper.selectByIds(StringUtils.join(pids, ",")).mapNotNull { it.resourceId }
                val token = Token()
                token.id = 0
                token.username = "guest"
                token.resources = resourceIds
                RedisUtil.set(Constant.Redis.TOKEN_NOT_LOGIN, token)
            }
        }
    }
    fun initResource(){
        val resources = resourceMapper.selectAll()
        RedisUtil.del(Constant.Redis.RESOURCE)
        RedisUtil.hMSet(Constant.Redis.RESOURCE,resources.associateBy({it.url},{it}))
    }
}
