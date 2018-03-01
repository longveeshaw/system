package com.basicfu.system.service

import com.github.pagehelper.PageInfo
import com.basicfu.system.common.Constant
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.mapper.PermissionResourceMapper
import com.basicfu.system.model.dto.PermissionDto
import com.basicfu.system.model.po.Permission
import com.basicfu.system.model.po.PermissionResource
import com.basicfu.system.model.vo.PermissionVo
import com.basicfu.common.util.RedisUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author fuliang
 * @date 17/10/31
 */
@Service
class PermissionService : BaseService<Permission>(Permission::class.java){
    @Autowired lateinit var prMapper: PermissionResourceMapper

    fun list(vo: PermissionVo): PageInfo<Any> {
        val result = selectPageDtoLike(arrayOf(arrayOf("id",vo.keyword), arrayOf("name",vo.keyword)), vo.pageNum, vo.pageSize)
        val pr= PermissionResource()
        result.list.forEach { e->
            e as PermissionDto
            pr.permissionId=e.id
            val ids=prMapper.select(pr).map { it.resourceId }
            e.rids=ids
        }
        return result
    }
    fun all():List<Any> = selectDto()
    fun insert(vo: PermissionVo):Int{
        if(vo.id!=null&&selectCountEqual(arrayOf("id", vo.id))>0)throw CustomException(Enum.Permission.EXIST_ID)
        if(selectCountEqual(arrayOf("name", vo.name))>0)throw CustomException(Enum.Permission.EXIST_NAME)
        val po= Permission()
        BeanUtils.copyProperties(vo,po)
        insertPoSelective(po)
        val prList= arrayListOf<PermissionResource>()
        vo.rids?.forEach{e->
            val pr=PermissionResource()
            pr.permissionId=po.id
            pr.resourceId=e
            prList.add(pr)
        }
        if(prList.size>0)prMapper.insertListNoUsePrimaryKey(prList)
        return 1
    }
    fun update(vo: PermissionVo):Int{
        val checkPermission=selectOne(arrayOf("name",vo.name))
        if(checkPermission!=null&&checkPermission.id!=vo.id){
            throw CustomException(Enum.Permission.EXIST_NAME)
        }
        prMapper.deleteByPrimaryKey(vo.id)
        if(vo.rids?.size!=0){
            val prList= arrayListOf<PermissionResource>()
            vo.rids?.forEach{e->
                val pr=PermissionResource()
                pr.permissionId=vo.id
                pr.resourceId=e
                prList.add(pr)
            }
            if(prList.size>0)prMapper.insertListNoUsePrimaryKey(prList)
        }
        return updateVoByPrimaryKeySelective(vo)
    }
    fun delete(ids:List<Long>):Int{
        if(ids.isNotEmpty()){
            prMapper.deleteByIds(StringUtils.join(ids,","))
        }
        return deleteByPrimaryKeyIds(ids)
    }
}