package com.basicfu.system.model.po

import javax.persistence.*

class User {
    /**
     * 主键
     */
    /**
     * 获取主键
     *
     * @return id - 主键
     */
    /**
     * 设置主键
     *
     * @param id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    /**
     * 用户名
     */
    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    var username: String? = null

    /**
     * 真实姓名
     */
    /**
     * 获取真实姓名
     *
     * @return nickname - 真实姓名
     */
    /**
     * 设置真实姓名
     *
     * @param nickname 真实姓名
     */
    var nickname: String? = null

    /**
     * 登录密码
     */
    /**
     * 获取登录密码
     *
     * @return password - 登录密码
     */
    /**
     * 设置登录密码
     *
     * @param password 登录密码
     */
    var password: String? = null

    /**
     * 盐
     */
    /**
     * 获取盐
     *
     * @return salt - 盐
     */
    /**
     * 设置盐
     *
     * @param salt 盐
     */
    var salt: String? = null

    /**
     * 手机号码
     */
    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    var mobile: String? = null

    /**
     * 注册时间
     */
    /**
     * 获取注册时间
     *
     * @return cdate - 注册时间
     */
    /**
     * 设置注册时间
     *
     * @param cdate 注册时间
     */
    var cdate: Int? = null

    /**
     * 最后一次修改时间
     */
    /**
     * 获取最后一次修改时间
     *
     * @return udate - 最后一次修改时间
     */
    /**
     * 设置最后一次修改时间
     *
     * @param udate 最后一次修改时间
     */
    var udate: Int? = null

    /**
     * 0正常,1删除
     */
    /**
     * 获取0正常,1删除
     *
     * @return status - 0正常,1删除
     */
    /**
     * 设置0正常,1删除
     *
     * @param status 0正常,1删除
     */
    var status: Byte? = null
}