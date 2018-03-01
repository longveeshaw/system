package com.basicfu.system.service

import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.mapper.DictMapper
import com.basicfu.system.model.dto.DictDto
import com.basicfu.system.model.po.Dict
import com.basicfu.system.model.vo.DictVo
import com.github.pagehelper.PageInfo
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tk.mybatis.mapper.entity.Example

/**
 * @author fuliang
 * @date 18/2/28
 */
@Service
class DictService : BaseService<Dict>(Dict::class.java) {
    @Autowired
    lateinit var dictMapper: DictMapper

    fun all(): List<Any> {
        val all = selectEqual(arrayOf(arrayOf("isdel",0)))
        val result = ArrayList<DictDto>()
        all.filter { it.lvl == 0 }.forEach { e ->
            val dto = DictDto()
            BeanUtils.copyProperties(e, dto)
            dto.value=dto.id.toString()
            dto.pid=0
            result.add(dto)
        }
        result.forEach { e ->
            val children = ArrayList<DictDto>()
            all.forEach { ee ->
                if (ee.lft in e.lft!!..e.rgt!! && ee.lvl == e.lvl!! + 1) {
                    val dto = DictDto()
                    BeanUtils.copyProperties(ee, dto)
                    chidren(dto, all)
                    dto.value=dto.id.toString()
                    dto.pid=e.id
                    children.add(dto)
                }
            }
            e.children = children
        }
        return result
    }
    fun list(vo:DictVo): PageInfo<Any>{
        return selectPageDtoEqual(arrayOf(arrayOf("name",vo.name), arrayOf("isdel",0)),vo.pageNum,vo.pageSize)
    }
    /**
     * 叶子节点下增加节点，添加到子节点的最后一个
     */
    fun insert(vo: DictVo): Int {
        val example = Example(Dict::class.java)
        example.selectProperties("rgt,lvl").isForUpdate = true
        example.createCriteria().andEqualTo("id", vo.id).andEqualTo("isdel",0)
        val one = dictMapper.selectByExample(example)
        if (one.isEmpty()) throw CustomException(Enum.Dict.NOT_FOUND)
        val tmp = one[0]
        dictMapper.updateCustomSql("set rgt=rgt+2 where rgt>=${tmp.rgt} and isdel=0")
        dictMapper.updateCustomSql("set lft=lft+2 where lft>${tmp.rgt} and isdel=0")
        val po = Dict()
        po.name = vo.name
        po.fixed = vo.fixed
        po.lft = tmp.rgt!!
        po.rgt = tmp.rgt!! + 1
        po.lvl = tmp.lvl!! + 1
        dictMapper.insertSelective(po)
        return 1
    }

    /**
     * 只能更新name和是否编辑
     */
    fun update(vo:DictVo):Int{
        val po=Dict()
        po.id=vo.id
        po.name=vo.name
        po.fixed=vo.fixed
        return dictMapper.updateByPrimaryKeySelective(po)
    }

    /**
     * 删除节点自动删除子节点
     */
    fun delete(ids:List<Long>):Int{
        ids.forEach { id->
            if(id==1L){
                throw CustomException(Enum.Dict.NO_DELETE_ROOT)
            }
            val example = Example(Dict::class.java)
            example.selectProperties("lft,rgt").isForUpdate = true
            example.createCriteria().andEqualTo("id", id).andEqualTo("isdel",0)
            val one = dictMapper.selectByExample(example)
            if (!one.isEmpty()){
                val tmp = one[0]
                val width= tmp.rgt!! - tmp.lft!! +1
                val updatePo=Dict()
                updatePo.isdel=1
                val exampleDelete = Example(Dict::class.java)
                exampleDelete.and().andBetween("lft",tmp.lft,tmp.rgt)
                dictMapper.updateByExampleSelective(updatePo,exampleDelete)
                dictMapper.updateCustomSql("set rgt = rgt -$width where rgt>${tmp.rgt} and isdel=0")
                dictMapper.updateCustomSql("set lft = lft -$width where lft>${tmp.rgt} and isdel=0")
            }
        }
        return 1
    }

    fun chidren(dict: DictDto, list: List<Dict>) {
        val children = ArrayList<DictDto>()
        list.forEach { ee ->
            if (ee.lft in dict.lft!!..dict.rgt!! && ee.lvl == dict.lvl!! + 1) {
                val dto = DictDto()
                BeanUtils.copyProperties(ee, dto)
                chidren(dto, list)
                dto.value=dto.id.toString()
                dto.pid=dict.id
                children.add(dto)
            }
        }
        dict.children = children
    }
}