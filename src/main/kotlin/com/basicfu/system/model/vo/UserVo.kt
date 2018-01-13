package com.basicfu.system.model.vo

import com.alibaba.fastjson.JSONObject
import com.basicfu.system.common.CustomException
import com.basicfu.system.common.Enum
import com.basicfu.common.util.RegexUtil

class UserVo:BaseVo() {
    var id: Long? = null
    var username: String? = null
    var nickname: String? = null
    var password: String? = null
    var mobile: String? = null
    var cdate: Int? = null
    var udate: Int? = null

    var keyword:String?=null
    var rids:List<Long?>?=null

    fun verfiyInsert() {
        val result = JSONObject()
        if (!RegexUtil.isUsername(username)) {
            result.put("username", "用户名为6-20个字符,仅限英文、数字和下划线")
        }
        if (nickname == null) {
            result.put("nickname", "nickname不能为空")
        }
        if (!RegexUtil.isPassword(password)) {
            result.put("password", "密码为6-20个字符,仅限英文、数字和标点符号")
        }
        if (!RegexUtil.isMobile(mobile)) {
            result.put("mobile", "手机号格式不正确")
        }
        if (rids==null) {
            result.put("rids", "rids不能为空")
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
        if (!RegexUtil.isUsername(username)) {
            result.put("username", "用户名为6-20个字符,仅限英文、数字和下划线")
        }
        if (nickname == null) {
            result.put("nickname", "nickname不能为空")
        }
        if (password!=null&&!RegexUtil.isPassword(password)) {
            result.put("password", "密码为6-20个字符,仅限英文、数字和标点符号")
        }
        if (!RegexUtil.isMobile(mobile)) {
            result.put("mobile", "手机号格式不正确")
        }
        if (rids == null) {
            result.put("rids", "rids不能为空")
        }
        if (result.size > 0) {
            throw CustomException(Enum.MISSING_PARAMETER, result)
        }
    }
}