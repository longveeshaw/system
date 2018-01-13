package com.basicfu.system.model.vo
/**
 * @author fuliang
 * @date 17/10/26
 */
open class BaseVo {
    /**
     * 当前页码
     */
    var pageNum: Int = 1
    /**
     * 每页条数
     * 后台限制每页最多100条
     */
    var pageSize: Int = 20
        set(value) {
            field =if(pageSize > 100)100 else value
        }
}
