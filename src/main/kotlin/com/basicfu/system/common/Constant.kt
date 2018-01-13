package com.basicfu.system.common

/**
 * @author fuliang
 * @date 17/10/26
 */
class Constant {
    object System{
        val SESSIN_TIMEOUT = 1800000L
    }
    object Redis{
        val TOKEN = "redis_token"
        val TOKEN_NOT_LOGIN = "redis_token_not_login"
        val RESOURCE = "redis_resource"
    }
}