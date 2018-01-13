package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.MenuVo
import com.basicfu.system.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * @author fuliang
 * @date 17/10/31
 */
@RestController
@RequestMapping("/menu")
class MenuContoller {
    @Autowired lateinit var menuService: MenuService

    @GetMapping("/list")
    fun list(vo: MenuVo): Result {
        return Result.success(menuService.list(vo))
    }

    @GetMapping("/all")
    fun all(): Result {
        return Result.success(menuService.all())
    }

    @GetMapping("/rlist")
    fun list(request: HttpServletRequest): Result {
        val token = request.getHeader("Authorization")
        return Result.success(menuService.rlist(token))
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: MenuVo): Result {
        vo.verfiyInsert()
        menuService.insert(vo)
        return Result.insert
    }

    @PostMapping("/update")
    fun update(@RequestBody vo: MenuVo): Result {
        vo.verfiyUpdate()
        menuService.update(vo)
        return Result.update
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids:List<Long>): Result {
        menuService.delete(ids)
        return Result.delete
    }
}