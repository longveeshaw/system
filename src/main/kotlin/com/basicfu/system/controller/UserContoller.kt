package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.UserVo
import com.basicfu.system.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * @author fuliang
 * @date 17/11/8
 */
@RestController
@RequestMapping("/user")
class UserContoller {
    @Autowired lateinit var userService: UserService

    @PostMapping("/login")
    fun login(@RequestBody vo:UserVo): Result {
        return Result.success(userService.login(vo))
    }
    @GetMapping("/logout")
    fun logout(request:HttpServletRequest): Result {
        val token = request.getHeader("Authorization")
        userService.logout(token)
        return Result.logout
    }
    @GetMapping("/list")
    fun list(vo: UserVo): Result {
        return Result.success(userService.list(vo))
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: UserVo): Result {
        vo.verfiyInsert()
        userService.insert(vo)
        return Result.insert
    }

    @PostMapping("/update")
    fun update(@RequestBody vo: UserVo): Result {
        vo.verfiyUpdate()
        userService.update(vo)
        return Result.update
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids:List<Long>): Result {
        userService.delete(ids)
        return Result.delete
    }

}