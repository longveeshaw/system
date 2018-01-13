package com.basicfu.system.common

import com.alibaba.fastjson.JSONObject

/**
 * @author fuliang
 * @date 17/10/26
 */
class CustomException : RuntimeException {
    var code: Int = 0
    var msg: String = ""
    var data: Any = JSONObject()

    internal fun deal(enum:Any){
        val java = enum::class.java
        val value = java.getDeclaredField("value")
        value.isAccessible=true
        this.code=value.get(enum).toString().toInt()
        val msg=java.getDeclaredField("msg")
        msg.isAccessible=true
        this.msg=msg.get(enum).toString()
    }
    constructor(enum: Any) {
        deal(enum)
    }
    constructor(enum: Any,data:Any) {
        deal(enum)
        this.data=data
    }
}
