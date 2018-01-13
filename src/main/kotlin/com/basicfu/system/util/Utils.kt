package com.basicfu.common.util

import sun.misc.BASE64Encoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.emptyList


/**
 * @author fuliang
 * @date 2017/11/15
 */
object Utils {
    val dateTimeFormat: SimpleDateFormat =SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    fun currentTimeSecond():Int{
        return (System.currentTimeMillis() / 1000).toInt()
    }
    fun unixMillsToSeconds(m:Long):Int = (m/1000).toInt()
    fun generateToken():String{
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val str = "weiduoshengchanguanlixitong" + sdf.format(Calendar.getInstance().time) + UUID.randomUUID().toString()
        val md5 = MessageDigest.getInstance("MD5")
        val base64en = BASE64Encoder()
        return base64en.encode(md5.digest(str.toByteArray()))
    }
    fun getParamter(query: String, name: String): String? {
        val split = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (item in split) {
            val n = item.indexOf("=")
            if (item.substring(0, n) == name) {
                return item.substring(n + 1, item.length)
            }
        }
        return null
    }
    /**
     * listPo转listDto
     */
    fun poListToDtoList(any:List<Any>):List<Any>{
        return emptyList()
    }
}
