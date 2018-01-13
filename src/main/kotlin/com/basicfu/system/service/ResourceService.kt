package com.basicfu.system.service

import com.github.pagehelper.PageInfo
import com.basicfu.system.common.Constant
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.system.model.po.Resource
import com.basicfu.system.model.vo.ResourceVo
import com.basicfu.common.util.RedisUtil
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author fuliang
 * @date 17/10/27
 */
@Service
class ResourceService:BaseService<Resource>(Resource::class.java){

    fun list(vo:ResourceVo): PageInfo<Any> {
        return selectOrLike(arrayListOf("id","name","url"),vo.keyword,vo.pageNum,vo.pageSize)
    }
    fun all():List<Any>{
        return selectAllDto()
    }
    fun insert(vo:ResourceVo):Int{
        if(vo.id!=null&&selectCount("id",vo.id)>0)throw CustomException(Enum.Resource.EXIST_ID)
        if(selectCount("url",vo.url)>0)throw CustomException(Enum.Resource.EXIST_URL)
        val po=Resource()
        BeanUtils.copyProperties(vo,po)
        insertPoSelective(po)
        RedisUtil.hSet(Constant.Redis.RESOURCE, po.url!!,po)
        return 1
    }
    fun update(vo:ResourceVo):Int{
        //更新url不能重复
        val checkUrl = selectOne("url",vo.url)
        if(checkUrl!=null&&checkUrl.id!=vo.id)throw CustomException(Enum.Resource.EXIST_URL)
        val po=Resource()
        BeanUtils.copyProperties(vo,po)
        //如果url变动则查询原有resource对象，redis删除已经存在的对象
        var resource:Resource? =null
        if(checkUrl==null||!vo.url.equals(checkUrl.url)){
            resource=selectByPrimaryKey(po.id!!)
        }
        if(updatePoByPrimaryKeySelective(po)>0){
            //如果url没有修改直接填入新的对象覆盖，否则删除原有并填入新的对象
            if(checkUrl!=null&&vo.url.equals(checkUrl.url)){
                RedisUtil.hSet(Constant.Redis.RESOURCE, po.url!!, po)
            }else{
                RedisUtil.hDel(Constant.Redis.RESOURCE, resource!!.url!!)
                RedisUtil.hSet(Constant.Redis.RESOURCE, po.url!!, po)
            }
        }
        return 1
    }
    fun delete(ids:List<Long>):Int{
        //resource是url当key所有要遍历
        if(ids.isNotEmpty()){
            val resourceMap = RedisUtil.hGetAll(Constant.Redis.RESOURCE) as Map<String, Resource>
            resourceMap.values.forEach { e ->
                if (ids.contains(e.id)) {
                    RedisUtil.hDel(Constant.Redis.RESOURCE, e.url!!)
                }
            }
        }
        return deleteByPrimaryKeyIds(ids)
    }
}