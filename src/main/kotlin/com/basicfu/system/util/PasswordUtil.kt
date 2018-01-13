package com.basicfu.common.util

import org.apache.shiro.crypto.SecureRandomNumberGenerator
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.util.ByteSource


object PasswordUtil {
    private val algorithmName = "md5"
    private val hashIterations = 2

    fun encryptPassword(username: String,password: String): PasswordSalt {
        val passwordSalt=PasswordSalt()
        passwordSalt.salt = SecureRandomNumberGenerator().nextBytes().toHex()
        val newPassword = SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(username + passwordSalt.salt),
                hashIterations).toHex()
        passwordSalt.password = newPassword
        return passwordSalt
    }

    fun checkPassword(username: String, password: String, encryptPassword: String, salt: String): Boolean {
        val newPassword = SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(username + salt),
                hashIterations).toHex()
        return encryptPassword == newPassword
    }
    class PasswordSalt{
        var password:String?=null
        var salt:String?=null
    }
}
