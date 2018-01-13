package com.basicfu.system.model.vo

import com.alibaba.fastjson.JSONObject
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum

class RoleVo:BaseVo() {
    var id: Long? = null
    var name: String? = null

    var keyword:String?=null
    var rpids:List<Long?>?=null
    var rmids:List<Long?>?=null
    var rtids:List<Long?>?=null

    fun verfiyInsert() {
        val result = JSONObject()
        if (name == null) {
            result.put("name", "name不能为空")
        }
        if (result.size > 0) {
            throw CustomException(Enum.MISSING_PARAMETER, result)
        }
    }
    fun verfiyUpdate() {
        val result = JSONObject()
        if (id == null) {
            result.put("id", "id不能为空")
        }
        if (name == null) {
            result.put("name", "name不能为空")
        }
        if (result.size > 0) {
            throw CustomException(Enum.MISSING_PARAMETER, result)
        }
    }
}