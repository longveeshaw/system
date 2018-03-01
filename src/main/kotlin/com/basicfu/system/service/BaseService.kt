package com.basicfu.system.service

import com.basicfu.system.common.CustomMapper
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
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

    lateinit var c: Class<T>

    constructor()

    constructor(cl: Class<T>) {
        this.c = cl
    }

    /**
     * 查询所有
     */
    fun selectAll(): MutableList<T> {
        return mapper.selectAll()
    }

    /**
     * 根据主键查询
     */
    fun selectByPrimaryKey(key: Any): T {
        return mapper.selectByPrimaryKey(key)
    }

    /**
     * 根据主键查询返回dto
     */
    fun selectByPrimaryKeyDto(key: Any): Any {
        val po = mapper.selectByPrimaryKey(key)
        val dtoInstance = Class.forName(c.name.replace(".po", ".dto") + "Dto").newInstance()
        BeanUtils.copyProperties(po, dtoInstance)
        return dtoInstance
    }

    /**
     * 根据ids查询
     */
    fun selectByIds(ids: List<Long>?): MutableList<T> {
        return if (ids?.isNotEmpty() == true) {
            mapper.selectByIds(StringUtils.join(ids, ","))
        } else {
            arrayListOf()
        }
    }

    /**
     * select page
     */
    fun selectPageEqual(equalProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<T> {
        return selectPage(equalProperty, null, null, pageNum, pageSize)
    }

    fun selectPageLike(likeProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<T> {
        return selectPage(null, likeProperty, null, pageNum, pageSize)
    }

    fun selectPageOrderBy(orderByProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<T> {
        return selectPage(null, null, orderByProperty, pageNum, pageSize)
    }

    fun selectPage(pageNum: Int, pageSize: Int): PageInfo<T> {
        return selectPage(null, null, null, pageNum, pageSize)
    }

    /**
     * select
     */
    fun selectEqual(equalProperty: Array<Array<*>>): List<T> {
        return select(equalProperty, null, null)
    }

    fun selectLike(likeProperty: Array<Array<*>>): List<T> {
        return select(null, likeProperty, null)
    }

    fun selectOrderBy(orderByProperty: Array<Array<*>>): List<T> {
        return select(null, null, orderByProperty)
    }

    fun select(): List<T> {
        return select(null, null, null)
    }

    /**
     * select page dto
     */
    fun selectPageDtoEqual(equalProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<Any> {
        return selectPageDto(equalProperty, null, null, pageNum, pageSize)
    }

    fun selectPageDtoLike(likeProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<Any> {
        return selectPageDto(null, likeProperty, null, pageNum, pageSize)
    }

    fun selectPageDtoOrderBy(orderByProperty: Array<Array<*>>, pageNum: Int, pageSize: Int): PageInfo<Any> {
        return selectPageDto(null, null, orderByProperty, pageNum, pageSize)
    }

    fun selectPageDto(pageNum: Int, pageSize: Int): PageInfo<Any> {
        return selectPageDto(null, null, null, pageNum, pageSize)
    }

    /**
     * select dto
     */
    fun selectDtoEqual(equalProperty: Array<Array<*>>): List<Any> {
        return selectDto(equalProperty, null, null)
    }

    fun selectDtoLike(likeProperty: Array<Array<*>>): List<Any> {
        return selectDto(null, likeProperty, null)
    }

    fun selectDtoOrderBy(orderByProperty: Array<Array<*>>): List<Any> {
        return selectDto(null, null, orderByProperty)
    }

    fun selectDto(): List<Any> {
        return selectDto(null, null, null)
    }

    /**
     * s
     * 字段值为null不处理
     */
    fun selectDto(equalProperty: Array<Array<*>>?, likeProperty: Array<Array<*>>?, orderByProperty: Array<Array<*>>?): List<Any> {
        val list = select(equalProperty, likeProperty, orderByProperty)
        val dtoList = mutableListOf<Any>()
        list.forEach { e ->
            val dtoInstance = Class.forName(c.name.replace(".po", ".dto") + "Dto").newInstance()
            BeanUtils.copyProperties(e, dtoInstance)
            dtoList.add(dtoInstance)
        }
        return dtoList
    }

    fun selectPageDto(equalProperty: Array<Array<*>>?, likeProperty: Array<Array<*>>?, orderByProperty: Array<Array<*>>?, pageNum: Int, pageSize: Int): PageInfo<Any> {
        val pageList = selectPage(equalProperty, likeProperty, orderByProperty, pageNum, pageSize) as PageInfo<Any>
        val dtoList = mutableListOf<Any>()
        pageList.list.forEach { e ->
            val dtoInstance = Class.forName(c.name.replace(".po", ".dto") + "Dto").newInstance()
            BeanUtils.copyProperties(e, dtoInstance)
            dtoList.add(dtoInstance)
        }
        pageList.list = dtoList
        return pageList
    }

    fun select(equalProperty: Array<Array<*>>?, likeProperty: Array<Array<*>>?, orderByProperty: Array<Array<*>>?): List<T> {
        if (equalProperty == null && likeProperty == null && orderByProperty == null) {
            return mapper.selectAll()
        } else {
            val example = Example(c)
            val criteria = example.createCriteria()
            equalProperty?.forEach { e ->
                criteria.andEqualTo(e[0].toString(), e[1])
            }
            likeProperty?.forEach { e ->
                criteria.andLike(e[0].toString(), "%${e[1]}%")
            }
            orderByProperty?.forEach { e ->
                if (e[1].toString().toLowerCase() == "asc") {
                    example.orderBy(e[0].toString()).asc()
                } else {
                    example.orderBy(e[0].toString()).desc()
                }
            }
            return mapper.selectByExample(example)
        }
    }

    fun selectPage(equalProperty: Array<Array<*>>?, likeProperty: Array<Array<*>>?, orderByProperty: Array<Array<*>>?, pageNum: Int, pageSize: Int): PageInfo<T> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        if (equalProperty == null && likeProperty == null && orderByProperty == null) {
            return PageInfo(mapper.selectAll())
        } else {
            val example = Example(c)
            val criteria = example.createCriteria()
            equalProperty?.forEach { e ->
                if(e[1]!=null){
                    criteria.andEqualTo(e[0].toString(), e[1])
                }
            }
            likeProperty?.forEach { e ->
                if(e[1]!=null){
                    criteria.andLike(e[0].toString(), "%${e[1]}%")
                }
            }
            orderByProperty?.forEach { e ->
                if (e[1].toString().toLowerCase() == "asc") {
                    example.orderBy(e[0].toString()).asc()
                } else {
                    example.orderBy(e[0].toString()).desc()
                }
            }
            return PageInfo(mapper.selectByExample(example))
        }
    }

    fun selectCount(): Int {
        return selectCount(null, null)
    }

    fun selectCountEqual(vararg equalProperty: Array<*>): Int {
        return selectCount(equalProperty.map { it }.toTypedArray(), null)
    }

    fun selectCountLike(vararg likeProperty: Array<Array<*>>): Int {
        return selectCount(null, arrayOf(likeProperty))
    }

    /**
     * 根据不同的字段，不同的value查询条数
     */
    fun selectCount(equalProperty: Array<Array<*>>?, likeProperty: Array<Array<*>>?): Int {
        return if (equalProperty == null && likeProperty == null) {
            mapper.selectCount(null)
        } else {
            val example = Example(c)
            val criteria = example.createCriteria()
            equalProperty?.forEach { e ->
                criteria.andEqualTo(e[0].toString(), e[1])
            }
            likeProperty?.forEach { e ->
                criteria.andLike(e[0].toString(), "%${e[1]}%")
            }
            mapper.selectCountByExample(example)
        }
    }

    /**
     * 根据不同的字段，不同的value查询对象
     */
    fun selectOne(vararg property: Array<Any?>): T? {
        val instance=c.newInstance()
        property.forEach { e ->
            val field = c.getDeclaredField(e[0].toString())
            field.isAccessible = true
            field.set(instance, e[1])
        }
        return mapper.selectOne(instance)
    }

    /**
     * vo选择性插入
     */
    fun insertVoSelective(vo: Any): Int {
        val instance = c.newInstance()
        BeanUtils.copyProperties(vo, instance)
        return mapper.insertSelective(instance)
    }

    /**
     * po选择性插入-如果没有主键会返回添加后的主键
     */
    fun insertPoSelective(po: T): Int {
        return mapper.insertSelective(po)
    }

    /**
     * po插入并返回主键
     */
    fun insertPoUseGeneratedKeys(po: T): Int {
        return mapper.insertUseGeneratedKeys(po)
    }

    /**
     * 根据主键po选择性更新其他值
     */
    fun updatePoByPrimaryKeySelective(po: T): Int {
        return mapper.updateByPrimaryKeySelective(po)
    }

    /**
     * 根据主键vo选择性更新其他值
     */
    fun updateVoByPrimaryKeySelective(vo: Any): Int {
        val instance = c.newInstance()
        BeanUtils.copyProperties(vo, instance)
        return mapper.updateByPrimaryKeySelective(instance)
    }

    /**
     * 根据自定义字段值更新对象
     */
    fun updateVoBySelectiveEqual(vo: Any,equalProperty: Array<Array<*>>): Int {
        val example = Example(c)
        val criteria = example.createCriteria()
        val java = vo::class.java
        val instance = c.newInstance()
        equalProperty.forEach { e->
            val field=java.getDeclaredField(e[0].toString())
            field.isAccessible=true
            field.set(vo,null)
            criteria.andEqualTo(e[0].toString(),e[1].toString())
        }
        BeanUtils.copyProperties(vo, instance)
        return mapper.updateByExampleSelective(instance,example)
    }
    fun deleteInAndEqual(inProperty: Array<*>?, equalProperty: Array<*>?): Int {
        val example = Example(c)
        val criteria = example.createCriteria()
        if (inProperty != null) {
            criteria.andIn(inProperty[0].toString(), inProperty[1] as List<*>)
        }
        if (equalProperty != null) {
            criteria.andEqualTo(equalProperty[0].toString(), equalProperty[1])
        }
        return mapper.deleteByExample(example)
    }

    fun deleteInAndEqualArray(inProperty: Array<Array<*>>?, equalProperty: Array<Array<*>>?): Int {
        val example = Example(c)
        val criteria = example.createCriteria()
        inProperty?.forEach { e ->
            criteria.andIn(e[0].toString(), e[1] as List<*>)
        }
        equalProperty?.forEach { e ->
            criteria.andEqualTo(e[0].toString(), e[1])
        }
        return mapper.deleteByExample(example)
    }

    fun deleteByPrimaryKeyIds(ids: List<Long>): Int {
        return when {
            ids.size == 1 -> mapper.deleteByPrimaryKey(ids[0])
            ids.size > 1 -> mapper.deleteByIds(StringUtils.join(ids, ","))
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