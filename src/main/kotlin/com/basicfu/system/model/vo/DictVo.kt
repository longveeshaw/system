package com.basicfu.system.model.vo

import com.alibaba.fastjson.JSONObject
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum

class DictVo:BaseVo() {

    var id: Long? = null

    var name: String? = null

    var value: String? = null

    var lft: Long? = null

    var rgt: Long? = null

    var lvl: Int? = null

    var fixed: Int? = null

    var isdel: Int? = null

    fun verfiyInsert() {
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