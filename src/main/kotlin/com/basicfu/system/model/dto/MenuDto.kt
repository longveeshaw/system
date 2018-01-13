package com.basicfu.system.model.dto

class MenuDto{
    var id: Long? = null
    var pid: Long? = null
    var name: String? = null
    var url: String? = null
    var sort: Int? = null
    var icon: String? = null

    var value:String?=null
    var pname:String?=null
    var children: List<MenuDto>?=null
}