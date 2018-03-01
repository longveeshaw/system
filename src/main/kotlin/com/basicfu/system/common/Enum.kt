package com.basicfu.system.common

/**
 * @author fuliang
 * @date 17/10/26
 */
enum class Enum constructor(val value: Int,val msg:String) {
    SUCCESS(0,"成功"),
    SERVER_ERROR(1,"服务器内部发生错误"),
    INVALID_PARAMETER(2,"无效的参数"),
    MISSING_PARAMETER(3,"缺少必要的参数"),
    INVALID_PERMISSION(4,"无权操作"),
    LOGIN_TIMEOUT(5,"登录超时");
    enum class FiledType {
        STRING(),
        NUMBER(),
        DATE(),
        TIME(),
        DATETIME(),
        DEVICE(),
        NO(),
    }
    enum class Order {
        ASC(),
        DESC(),
    }
    enum class User constructor(val value: Int,val msg:String) {
        NOT_FOUND_USER(211,"用户名不存在"),
        EXIST_USER(211,"用户名已存在"),
        EXIST_MOBILE(211,"手机号已存在"),
        NOT_DELETE_SUPERADMIN(211,"超级管理员无法删除"),
        INVALID_PASSWORD(211,"密码错误");
    }
    enum class Role constructor(val value: Int,val msg:String) {
        EXIST_ID(101,"ID已存在"),
        EXIST_NAME(101,"角色名已存在");
    }
    enum class Permission constructor(val value: Int,val msg:String) {
        EXIST_ID(100,"权限ID已存在"),
        EXIST_NAME(101,"权限名已存在");
    }
    enum class Resource constructor(val value: Int,val msg:String) {
        EXIST_ID(100,"资源ID已存在"),
        EXIST_URL(101,"资源URL已存在");
    }
    enum class Menu constructor(val value: Int,val msg:String) {
        EXIST_ID(200,"菜单ID已存在"),
        ID_PID_REPEAT(200,"父菜单不能是当前菜单"),
        EXIST_NAME(201,"菜单名已存在");
    }
    enum class Dict constructor(val value: Int,val msg:String) {
        NOT_FOUND(201,"找不到字典"),
        ID_PID_REPEAT(202,"父类型不能是当前类型"),
        NO_DELETE_ROOT(202,"不能删除根字典"),
    }
    enum class TableWord constructor(val value: Int,val msg:String) {
        EXIST_NAME(201,"同类型下的文字表格名不能重复"),
        PARSE_ERROR(202,"解析word出错，请检查文件是否正确");
    }
    enum class TableList constructor(val value: Int,val msg:String) {
        EXIST_NAME(201,"同类型下的列表表格名不能重复"),
        REPEAT_NAME(201,"表格字段名不能重复"),
        INVALID_LENGTH(201,"无效的长度"),
        TOTAL_LIMIT(201,"值区间在-9223372036854775807~9223372036854775807"),
        DECIMAL_LIMIT(201,"超出最大长度20位"),
        DECIMAL_THAN_TOTAL(201,"小数位不能大于总位数"),
        INVALID_DATA(201,"填写的数据违反了规范的约定");
    }
    enum class Device constructor(val value: Int,val msg:String) {
        EXIST_NAME(201,"同型号设备名已存在");
    }
    enum class No constructor(val value: Int,val msg:String) {
        EXIST_NAME(201,"批号已存在");
    }
    enum class StockData constructor(val value: Int,val msg:String) {
        AlREADY_EXISTS(201,"同批次同类型的库存名已经存在"),
        CANT_BE_SMALLER_THEN_ZERO(201,"库存数量不能唯负");
    }
}
