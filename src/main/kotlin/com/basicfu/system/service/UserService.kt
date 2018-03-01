package com.basicfu.system.service

import com.alibaba.fastjson.JSONObject
import com.github.pagehelper.PageInfo
import com.basicfu.system.common.Constant
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.common.Token
import com.basicfu.system.mapper.PermissionResourceMapper
import com.basicfu.system.mapper.RoleMenuMapper
import com.basicfu.system.mapper.RolePermissionMapper
import com.basicfu.system.mapper.UserRoleMapper
import com.basicfu.system.model.dto.UserDto
import com.basicfu.system.model.po.User
import com.basicfu.system.model.po.UserRole
import com.basicfu.system.model.vo.UserVo
import com.basicfu.common.util.PasswordUtil
import com.basicfu.common.util.RedisUtil
import com.basicfu.common.util.Utils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author fuliang
 * @date 17/10/31
 */
@Service
class UserService : BaseService<User>(User::class.java){
    @Autowired lateinit var urMapper: UserRoleMapper
    @Autowired lateinit var rpMapper: RolePermissionMapper
    @Autowired lateinit var rmMapper: RoleMenuMapper
    @Autowired lateinit var prMapper: PermissionResourceMapper

    fun login(vo:UserVo):Any{
        val user = selectOne(arrayOf("username", vo.username)) ?: throw CustomException(Enum.User.NOT_FOUND_USER)
        val checkPassword = PasswordUtil.checkPassword(vo.username!!, vo.password!!, user.password!!, user.salt!!)
        if(!checkPassword)throw CustomException(Enum.User.INVALID_PASSWORD)
        val result=JSONObject()
        val tokenStr = Utils.generateToken()
        result.put("username",user.username)
        result.put("token",tokenStr)
        result.put("login",true)
        //查询用户资源
        val rids=urMapper.selectByIds(user.id.toString()).mapNotNull { it.roleId }
        val mids=if(rids.isEmpty()) emptyList() else rmMapper.selectByIds(StringUtils.join(rids,",")).mapNotNull { it.menuId }
        val pids=if(rids.isEmpty()) emptyList() else rpMapper.selectByIds(StringUtils.join(rids,",")).mapNotNull { it.permissionId }
        val resourceIds=if(pids.isEmpty()) emptyList() else prMapper.selectByIds(StringUtils.join(pids,",")).mapNotNull{it.resourceId}
        val token=Token()
        token.id=user.id
        token.username=user.username
        token.menus=mids
        token.resources=resourceIds
        RedisUtil.set("${Constant.Redis.TOKEN}$tokenStr",token,Constant.System.SESSIN_TIMEOUT)
        return result
    }
    fun logout(token:String?){
        RedisUtil.del("${Constant.Redis.TOKEN}$token")
    }
    fun list(vo: UserVo): PageInfo<Any> {
        val result = selectPageDtoLike(arrayOf(arrayOf("id",vo.keyword), arrayOf("username",vo.keyword), arrayOf("mobile",vo.keyword)), vo.pageNum, vo.pageSize)
        val ur= UserRole()
        result.list.forEach { e->
            e as UserDto
            ur.userId=e.id
            val rids=urMapper.select(ur).map { it.roleId }
            e.rids=rids
        }
        return result
    }

    fun insert(vo: UserVo):Int{
        if(selectCountEqual(arrayOf("username",vo.username))>0){throw CustomException(Enum.User.EXIST_USER)}
        if(selectCountEqual(arrayOf("mobile",vo.mobile))>0){throw CustomException(Enum.User.EXIST_MOBILE)}
        val po=User()
        BeanUtils.copyProperties(vo,po)
        po.cdate= Utils.currentTimeSecond()
        po.udate=po.cdate
        po.status=0
        val encryptPassword = PasswordUtil.encryptPassword(po.username!!, po.password!!)
        po.salt=encryptPassword.salt
        po.password=encryptPassword.password
        insertPoUseGeneratedKeys(po)
        val urList= arrayListOf<UserRole>()
        vo.rids?.forEach { e->
            val ur =UserRole()
            ur.userId=po.id
            ur.roleId=e
            urList.add(ur)
        }
        if(urList.size!=0)urMapper.insertListNoUsePrimaryKey(urList)
        return 1
    }
    fun update(vo: UserVo):Int{
        val checkUser=selectOne(arrayOf("mobile",vo.mobile))
        if(checkUser!=null&&checkUser.id!=vo.id){
            throw CustomException(Enum.User.EXIST_MOBILE)
        }
        urMapper.deleteByPrimaryKey(vo.id)
        val urList= arrayListOf<UserRole>()
        vo.rids?.forEach { e->
            val ur =UserRole()
            ur.userId=vo.id
            ur.roleId=e
            urList.add(ur)
        }
        if(urList.size!=0)urMapper.insertListNoUsePrimaryKey(urList)
        val po=User()
        BeanUtils.copyProperties(vo,po)
        if(!po.password.isNullOrBlank()){
            val encryptPassword = PasswordUtil.encryptPassword(po.username!!, po.password!!)
            po.salt=encryptPassword.salt
            po.password=encryptPassword.password
        }
        return updatePoByPrimaryKeySelective(po)
    }
    fun delete(ids:List<Long>):Int{
        if(ids.isNotEmpty()){
            urMapper.deleteByIds(StringUtils.join(ids,","))
            if(ids.indexOf(10000L)!=-1){
                throw CustomException(Enum.User.NOT_DELETE_SUPERADMIN)
            }
        }
        return deleteByPrimaryKeyIds(ids)
    }
}