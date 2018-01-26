package com.basicfu.system.service

import com.basicfu.system.common.Constant
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.common.Token
import com.basicfu.system.model.dto.MenuDto
import com.basicfu.system.model.po.Menu
import com.basicfu.system.model.vo.MenuVo
import com.basicfu.common.util.RedisUtil
import com.github.pagehelper.PageInfo
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service

/**
 * @author fuliang
 * @date 17/10/31
 */
@Service
class MenuService : BaseService<Menu>(Menu::class.java) {
    /**
     * 当设置为访客无法访问此接口，既该token不可能为空
     */
    fun rlist(token:String?): List<Any> {
        val token = RedisUtil.get(Constant.Redis.TOKEN + token,Token::class.java)
        if(token!=null) {
            val menus=selectByIds(token.menus)
            val result = arrayListOf<MenuDto>()
            menus.sortBy { it.pid }
            menus.sortBy { it.sort }
            menus.forEach { e ->
                if (0L == e.pid) {
                    val dto = MenuDto()
                    BeanUtils.copyProperties(e, dto)
                    if(dto.url==null)dto.url=""
                    result.add(dto)
                }
            }
            result.forEach { e ->
                e.children = getChildren(e, menus)
            }
            return result
        }else{
            return emptyList()
        }
    }

    fun list(vo: MenuVo): PageInfo<Any> {
        val result = selectOrLike(arrayListOf("id","name","url"), vo.keyword, vo.pageNum, vo.pageSize)
        val map = selectAll().associateBy({ it.id }, { it })
        result.list.forEach{e->
            val menuDto = e as MenuDto
            e.pname=map[menuDto.pid]?.name
        }
        var list = result.list as List<MenuDto>
        list=list.sortedWith(compareBy(MenuDto::pid, { it.sort }))
        result.list=list
        return result
    }
    fun all(): List<Any> {
        val result = arrayListOf<MenuDto>()
        val menus = selectAll()
        menus.sortBy { it.pid }
        menus.sortBy { it.sort }
        menus.forEach { e ->
            if (0L == e.pid) {
                val dto = MenuDto()
                BeanUtils.copyProperties(e, dto)
                dto.value=e.id.toString()
                result.add(dto)
            }
        }
        result.forEach { e ->
            e.children = getChildren(e, menus)
        }
        return result
    }

    fun insert(vo: MenuVo): Int {
        if (vo.id != null && selectCount("id", vo.id) > 0) throw CustomException(Enum.Menu.EXIST_ID)
        if (selectCount("name", vo.name) > 0) throw CustomException(Enum.Menu.EXIST_NAME)
        return insertVoSelective(vo)
    }

    fun update(vo: MenuVo): Int {
        if(vo.id==vo.pid)throw CustomException(Enum.Menu.ID_PID_REPEAT)
        val checkMenu=selectOne("name",vo.name)
        if(checkMenu!=null&&checkMenu.id!=vo.id){
            throw CustomException(Enum.Menu.EXIST_NAME)
        }
        return updateVoByPrimaryKeySelective(vo)
    }

    fun delete(ids: List<Long>): Int {
        return deleteByPrimaryKeyIds(ids)
    }

    private fun getChildren(m: MenuDto, menus: List<Menu>): List<MenuDto> {
        val childList = arrayListOf<MenuDto>()
        var flag = true
        menus.forEach { e ->
            if (e.pid == m.id) {
                flag = false
                val split = e.url?.split('/')
                m.url = split?.get(split.size - 2)
                val menu = MenuDto()
                BeanUtils.copyProperties(e, menu)
                menu.value=e.id.toString()
                menu.url = split?.get(split.size - 1)
                menu.children = getChildren(menu, menus)
                childList.add(menu)
            }
        }
        if (flag) {
            val split = m.url?.split('/')
            m.url = split?.get(split.size - 1)
        }else{
            if(m.url==null)m.url=""
        }
        return childList
    }
}