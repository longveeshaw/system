package com.basicfu.system.service

import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.mapper.DictMapper
import com.basicfu.system.model.po.Dict
import com.basicfu.system.model.vo.DictVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tk.mybatis.mapper.entity.Example

/**
 * @author fuliang
 * @date 18/2/28
 */
@Service
class DictService : BaseService<Dict>(Dict::class.java){
    @Autowired lateinit var dictMapper:DictMapper

    fun all():List<Any>{
        val all=selectAll()
        val max=all.maxWith(Comparator { p1, p2 ->
            when {
                p1.lft!! > p2.lft!! -> 1
                p1.lft == p2.lft -> 0
                else -> -1
            }
        })

        return emptyList()
    }

    /**
     * 叶子节点下增加节点，添加到子节点的最后一个
     */
    fun insert(vo: DictVo):Int{
        val example=Example(Dict::class.java)
        example.selectProperties("rgt,lvl").isForUpdate=true
        example.createCriteria().andEqualTo("id",vo.id)
        val one=dictMapper.selectByExample(example)
        if(one.isEmpty())throw CustomException(Enum.Dict.NOT_FOUND)
        val tmp=one[0]
        dictMapper.updateCustomSql("set rgt=rgt+2 where rgt>=${tmp.rgt}")
        dictMapper.updateCustomSql("set lft=lft+2 where lft>${tmp.rgt}")
        val po=Dict()
        po.name=vo.name
        po.fixed=vo.fixed
        po.lft= tmp.rgt!!
        po.rgt= tmp.rgt!! +1
        po.lvl= tmp.lvl!!+1
        dictMapper.insertSelective(po)
        return 1
    }

}