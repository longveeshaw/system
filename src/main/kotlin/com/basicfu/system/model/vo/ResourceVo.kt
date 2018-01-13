package com.basicfu.system.model.vo

import com.alibaba.fastjson.JSONObject
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum


class ResourceVo:BaseVo(){
    var id: Long? = null
    var url: String? = null
    var name: String? = null

    var keyword: String? = null

    fun verfiyInsert(){
        val result=JSONObject()
        if(url==null){
            result.put("url","url不能为空")
        }
        if(name==null){
            result.put("name","name不能为空")
        }
        if(result.size>0){
            throw CustomException(Enum.MISSING_PARAMETER,result)
        }
    }
    fun verfiyUpdate(){
        val result=JSONObject()
        if(id==null){
            result.put("id","id不能为空")
        }
        if(url==null){
            result.put("url","url不能为空")
        }
        if(name==null){
            result.put("name","name不能为空")
        }
        if(result.size>0){
            throw CustomException(Enum.MISSING_PARAMETER,result)
        }
    }
}