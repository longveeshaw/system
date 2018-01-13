package com.basicfu.system.model.dto

import com.alibaba.fastjson.JSONObject
import com.github.pagehelper.PageInfo
import java.io.Serializable
import java.util.*

/**
 * @author fuliang
 * @date 17/10/26
 */
class Result : Serializable {
    var code: Int? = 0
    var success = true
    var rid = UUID.randomUUID().toString()
    var msg = ""
    var data: Any = JSONObject()

    constructor(){
    }

    constructor(msg: String){
        this.msg=msg
    }

    companion object {
        val success = Result()
        val insert = Result("添加成功")
        val update = Result("更新成功")
        val delete = Result("删除成功")
        val logout = Result("退出成功")
        fun success(data: PageInfo<Any>): Result {
            val obj=JSONObject()
            obj.put("list",data.list)
            val page=JSONObject()
            page.put("total",data.total)
            page.put("pageSize",data.pageSize)
            page.put("pageNum",data.pageNum)
            obj.put("page",page)
            val result = Result()
            result.data = obj
            return result
        }
        fun success(data: List<Any>): Result {
            val result = Result()
            result.data = data
            return result
        }
        fun success(data: Any): Result {
            val result = Result()
            result.data = data
            return result
        }
        fun success(msg: String): Result {
            val result = Result()
            result.msg = msg
            return result
        }

        fun error(code: Int?, msg: String): Result {
            val result = Result()
            result.code = code
            result.success = false
            result.msg = msg
            return result
        }
        fun error(code: Int?, msg: String,data: Any): Result {
            val result = Result()
            result.code = code
            result.success = false
            result.msg = msg
            result.data = data
            return result
        }
    }
}
