package com.basicfu.system.model.vo

import com.alibaba.fastjson.JSONObject
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum

class MenuVo : BaseVo() {
    var id: Long? = null
    var pid: Long? = null
    var name: String? = null
    var url: String? = null
    var sort: Int? = null
    var icon: String? = null

    var keyword: String? = null

    fun verfiyInsert() {
        val result = JSONObject()
        if (name == null) {
            result.put("name", "name不能为空")
        }
        if (pid == null) {
            result.put("pid", "pid号不能为空")
        }
        if (result.size > 0) {
            throw CustomException(Enum.MISSING_PARAMETER, result)
        }
    }

    fun verfiyUpdate() {
        val result = JSONObject()
        if (id == null) {
            result.put("id", "ID不能为空")
        }
        if (name == null) {
            result.put("name", "name不能为空")
        }
        if (pid == null) {
            result.put("pid", "pid号不能为空")
        }
        if (result.size > 0) {
            throw CustomException(Enum.MISSING_PARAMETER, result)
        }
    }
}