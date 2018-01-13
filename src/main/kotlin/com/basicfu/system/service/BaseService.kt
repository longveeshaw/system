package com.basicfu.system.service

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.basicfu.system.common.CustomMapper
import com.basicfu.system.common.Enum
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import tk.mybatis.mapper.entity.Example


/**
 * @author fuliang
 * @date 17/10/29
 */
abstract class BaseService<T> {

    @Autowired
    protected lateinit var mapper: CustomMapper<T>

    lateinit var c:Class<T>

    constructor()

    constructor(cl:Class<T>){
        this.c=cl
    }

    /**
     * 查询所有
     */
    fun selectAll():MutableList<T>{
        return mapper.selectAll()
    }
    /**
     * 根据主键查询
     */
    fun selectByPrimaryKey(key:Any):T{
        return mapper.selectByPrimaryKey(key)
    }

    /**
     * 根据主键查询返回dto
     */
    fun selectByPrimaryKeyDto(key:Any):Any{
        val po=mapper.selectByPrimaryKey(key)
        val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
        BeanUtils.copyProperties(po,dtoInstance)
        return dtoInstance
    }
    /**
     * 根据ids查询
     */
    fun selectByIds(ids:List<Long>?):MutableList<T>{
        return if(ids?.isNotEmpty() == true){
            mapper.selectByIds(StringUtils.join(ids,","))
        }else{
            arrayListOf()
        }
    }
    /**
     * 查询所有并返回dto
     */
    fun selectAllDto():MutableList<Any>{
        val poList = mapper.selectAll()
        val dtoList= mutableListOf<Any>()
        poList.forEach { e->
            val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
            BeanUtils.copyProperties(e,dtoInstance)
            dtoList.add(dtoInstance)
        }
        return dtoList
    }
    /**
     * 输入po返回po
     */
    fun select(po: T,pageNumber: Int,pageSize:Int): List<T> {
        PageHelper.startPage<Any>(pageNumber,pageSize)
        return mapper.select(po)
    }

    /**
     * 输入po和分页处理后转dto返回
     */
    fun select(po: T,c: Class<T>,pageNum: Int,pageSize:Int): PageInfo<Any> {
        PageHelper.startPage<Any>(pageNum,pageSize)
        val poList=PageInfo<Any>(mapper.select(po) as List<*>?)
        val dtoList= mutableListOf<Any>()
        poList.list.forEach { e->
            val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
            BeanUtils.copyProperties(e,dtoInstance)
            dtoList.add(dtoInstance)
        }
        poList.list=dtoList
        return poList
    }
    /**
     * 或like和分页处理后转dto返回
     */
    fun selectOrLike(list:List<String>,keyword: Any?,pageNum: Int,pageSize:Int): PageInfo<Any> {
        PageHelper.startPage<Any>(pageNum,pageSize)
        val poList=PageInfo<Any>(if(list.isEmpty()||keyword==null || keyword==""){
            mapper.selectAll()
        }else{
            val example=Example(c)
            val createCriteria = example.createCriteria()
            list.forEach{e->
                createCriteria.orLike(e,"%$keyword%")
            }
            mapper.selectByExample(example)
        } as List<*>?)
        val dtoList= mutableListOf<Any>()
        poList.list.forEach { e->
            val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
            BeanUtils.copyProperties(e,dtoInstance)
            dtoList.add(dtoInstance)
        }
        poList.list=dtoList
        return poList
    }

    /**
     * 或like和分页且排序处理后转dto返回
     */
    fun selectOrLikeOrderBy(list:List<String>,keyword: Any?,pageNum: Int,pageSize:Int,orderField:String,order:Enum.Order): PageInfo<Any> {
        PageHelper.startPage<Any>(pageNum,pageSize)
        val poList=PageInfo<Any>(if(list.isEmpty()||keyword==null || keyword==""){
            val example=Example(c)
            if(order==Enum.Order.ASC){
                example.orderBy(orderField).asc()
            }else{
                example.orderBy(orderField).desc()
            }
            mapper.selectByExample(example)
        }else{
            val example=Example(c)
            val createCriteria = example.createCriteria()
            list.forEach{e->
                createCriteria.orLike(e,"%$keyword%")
            }
            if(order==Enum.Order.ASC){
                example.orderBy(orderField).asc()
            }else{
                example.orderBy(orderField).desc()
            }
            mapper.selectByExample(example)
        } as List<*>?)
        val dtoList= mutableListOf<Any>()
        poList.list.forEach { e->
            val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
            BeanUtils.copyProperties(e,dtoInstance)
            dtoList.add(dtoInstance)
        }
        poList.list=dtoList
        return poList
    }

    /**
     * 根据单个字段查询条数
     */
    fun selectCount(property:String,value:Any?):Int{
        val example=Example(c)
        example.createCriteria().andEqualTo(property,value)
        return mapper.selectCountByExample(example)
    }
    /**
     * 根据单个字段查询一个对象
     */
    fun selectOne(property:String,value:Any?):T?{
        if(value==null){
            return null
        }
        val example=Example(c)
        example.createCriteria().andEqualTo(property,value)
        val result = mapper.selectByExample(example)
        return if(result.size!=0){
            result[0]
        }else{
            null
        }
    }
    /**
     * 根据po查询一个对象
     */
    fun selectOne(po:T):T?{
        return mapper.selectOne(po)
    }
    /**
     * 根据单个字段查询对象列表
     */
    fun selectList(property:String,value:Any?):List<T>?{
        if(value==null){
            return null
        }
        val example=Example(c)
        example.createCriteria().andEqualTo(property,value)
        val result = mapper.selectByExample(example)
        return if(result.size!=0){
            result
        }else{
            null
        }
    }

    /**
     * vo选择性插入
     */
    fun insertVoSelective(vo: Any):Int{
        val instance = c.newInstance()
        BeanUtils.copyProperties(vo,instance)
        return mapper.insertSelective(instance)
    }
    /**
     * po选择性插入-如果没有主键会返回添加后的主键
     */
    fun insertPoSelective(po: T):Int{
        return mapper.insertSelective(po)
    }
    /**
     * po插入并返回主键
     */
    fun insertPoUseGeneratedKeys(po: T):Int{
        return mapper.insertUseGeneratedKeys(po)
    }

    /**
     * 根据主键po选择性更新其他值
     */
    fun updatePoByPrimaryKeySelective(po: T):Int{
        return mapper.updateByPrimaryKeySelective(po)
    }
    /**
     * 根据主键vo选择性更新其他值
     */
    fun updateVoByPrimaryKeySelective(vo: Any):Int{
        val instance = c.newInstance()
        BeanUtils.copyProperties(vo,instance)
        return mapper.updateByPrimaryKeySelective(instance)
    }

    fun deleteByPrimaryKeyIds(ids:List<Long>):Int{
        return when {
            ids.size==1 -> mapper.deleteByPrimaryKey(ids[0])
            ids.size>1 -> mapper.deleteByIds(StringUtils.join(ids, ","))
            else -> 0
        }
    }

//    /**
//     * 输入vo转po处理后并转dto返回
//     */
//    fun select(vo: Any,c: Class<T>): List<Any> {
//        val instance = c.newInstance()
//        BeanUtils.copyProperties(vo,instance)
//        val java = vo::class.java.superclass
//        val pageNum=java.getDeclaredField("pageNum")
//        pageNum.isAccessible=true
//        val pageSize=java.getDeclaredField("pageSize")
//        pageSize.isAccessible=true
//        PageHelper.startPage<Any>(pageNum.get(vo).toString().toInt(),pageSize.get(vo).toString().toInt())
//        val poList=mapper.select(instance as T)
//        val dtoList= arrayListOf<Any>()
//        poList.forEach { e->
//            val dtoInstance = Class.forName(c.name.replace(".po",".dto")+"Dto").newInstance()
//            BeanUtils.copyProperties(e,dtoInstance)
//            dtoList.add(dtoInstance)
//        }
//        return dtoList
//    }
}