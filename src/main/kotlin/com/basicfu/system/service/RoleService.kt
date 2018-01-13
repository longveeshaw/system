package com.basicfu.system.service

import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.mapper.RoleMenuMapper
import com.basicfu.system.mapper.RolePermissionMapper
import com.basicfu.system.model.dto.RoleDto
import com.basicfu.system.model.po.Role
import com.basicfu.system.model.po.RoleMenu
import com.basicfu.system.model.po.RolePermission
import com.basicfu.system.model.vo.RoleVo
import com.github.pagehelper.PageInfo
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author fuliang
 * @date 17/10/31
 */
@Service
class RoleService : BaseService<Role>(Role::class.java){
    @Autowired lateinit var rpMapper: RolePermissionMapper
    @Autowired lateinit var rmMapper: RoleMenuMapper

    fun list(vo: RoleVo): PageInfo<Any> {
        val result = selectOrLike(arrayListOf("id", "name"), vo.keyword, vo.pageNum, vo.pageSize)
        val dtoList=result.list as List<RoleDto>
        if(dtoList.isNotEmpty()){
            val rids=dtoList.map { it.id }
            val rpids=rpMapper.selectByIds(StringUtils.join(rids,",")).groupBy({ it.roleId },{it.permissionId})
            val rmids=rmMapper.selectByIds(StringUtils.join(rids,",")).groupBy({it.roleId},{ it.menuId })
            dtoList.forEach { e->
                e.rpids=rpids.get(e.id)
                e.rmids=rmids.get(e.id)
            }
        }
        return result
    }

    fun all():List<Any> = selectAllDto()
    fun insert(vo: RoleVo):Int{
        if(vo.id!=null){
            if(selectCount("id",vo.id)>0){throw CustomException(Enum.Role.EXIST_ID)}
        }
        if(selectCount("name",vo.name)>0){throw CustomException(Enum.Role.EXIST_NAME)}
        val po= Role()
        BeanUtils.copyProperties(vo,po)
        insertPoSelective(po)
        val rpList= arrayListOf<RolePermission>()
        val rmList= arrayListOf<RoleMenu>()
        vo.rpids?.forEach { e->
            val rp=RolePermission()
            rp.roleId=po.id
            rp.permissionId=e
            rpList.add(rp)
        }
        vo.rmids?.forEach { e->
            val rm=RoleMenu()
            rm.roleId=po.id
            rm.menuId=e
            rmList.add(rm)
        }
        if(rpList.size!=0)rpMapper.insertListNoUsePrimaryKey(rpList)
        if(rmList.size!=0)rmMapper.insertListNoUsePrimaryKey(rmList)
        return 1
    }
    fun update(vo: RoleVo):Int{
        val checkRole=selectOne("name",vo.name)
        if(checkRole!=null&&checkRole.id!=vo.id){
            throw CustomException(Enum.Role.EXIST_NAME)
        }
        rpMapper.deleteByPrimaryKey(vo.id)
        rmMapper.deleteByPrimaryKey(vo.id)
        val rpList= arrayListOf<RolePermission>()
        val rmList= arrayListOf<RoleMenu>()
        vo.rpids?.forEach { e->
            val rp=RolePermission()
            rp.roleId=vo.id
            rp.permissionId=e
            rpList.add(rp)
        }
        vo.rmids?.forEach { e->
            val rm=RoleMenu()
            rm.roleId=vo.id
            rm.menuId=e
            rmList.add(rm)
        }
        if(rpList.size!=0)rpMapper.insertListNoUsePrimaryKey(rpList)
        if(rmList.size!=0)rmMapper.insertListNoUsePrimaryKey(rmList)
        return updateVoByPrimaryKeySelective(vo)
    }
    fun delete(ids:List<Long>):Int{
        if(ids.isNotEmpty()){
            rpMapper.deleteByIds(StringUtils.join(ids,","))
            rmMapper.deleteByIds(StringUtils.join(ids,","))
        }
        return deleteByPrimaryKeyIds(ids)
    }
}