package com.basicfu.system.util

import java.io.IOException
import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @author basicfu
 * @date 2018/1/23
 */
object TaobaoUtil {
    private val CHARSET = "UTF-8"
    @Throws(IOException::class)
    @JvmOverloads
    fun signTopRequest(params: Map<String, String>, secret: String, signMethod: String = "hmac"): String {
        // 第一步：检查参数是否已经排序
        val keys = params.keys.toTypedArray()
        Arrays.sort(keys)
        // 第二步：把所有参数名和参数值串在一起
        val query = StringBuilder()
        if ("md5" == signMethod) {
            query.append(secret)
        }
        for (key in keys) {
            val value = params[key]
            if (!key.isEmpty() && !value!!.isEmpty()) {
                query.append(key).append(value)
            }
        }
        // 第三步：使用MD5/HMAC加密
        val bytes: ByteArray?
        if ("hmac" == signMethod) {
            bytes = encryptHMAC(query.toString(), secret)
        } else {
            query.append(secret)
            bytes = encryptMD5(query.toString())
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes!!)
    }

    @Throws(IOException::class)
    private fun encryptHMAC(data: String, secret: String): ByteArray? {
        var bytes: ByteArray? = null
        try {
            val secretKey = SecretKeySpec(secret.toByteArray(charset(CHARSET)), "HmacMD5")
            val mac = Mac.getInstance(secretKey.algorithm)
            mac.init(secretKey)
            bytes = mac.doFinal(data.toByteArray())
        } catch (gse: GeneralSecurityException) {
            throw IOException(gse.toString())
        }

        return bytes
    }

    @Throws(IOException::class)
    private fun encryptMD5(data: String): ByteArray {
        return encryptMD5(data.toByteArray(charset(CHARSET)))
    }

    @Throws(IOException::class)
    private fun encryptMD5(data: ByteArray): ByteArray {
        val var1: Any? = null
        try {
            val md = MessageDigest.getInstance("MD5")
            return md.digest(data)
        } catch (var3: GeneralSecurityException) {
            throw IOException(var3.toString())
        }

    }

    private fun byte2hex(bytes: ByteArray): String {
        val sign = StringBuilder()
        for (i in bytes.indices) {
            val hex = Integer.toHexString(bytes[i].toInt() and 0xFF)
            if (hex.length == 1) {
                sign.append("0")
            }
            sign.append(hex.toUpperCase())
        }
        return sign.toString()
    }
}
