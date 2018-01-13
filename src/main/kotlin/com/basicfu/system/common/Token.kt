package com.basicfu.system.common

import java.io.Serializable

/**
 * @author fuliang
 * @date 2017/11/17
 */
class Token : Serializable {
    var id: Long?=null
    var username: String? = null
    var menus: List<Long>? = null
    var resources: List<Long>? = null
}
