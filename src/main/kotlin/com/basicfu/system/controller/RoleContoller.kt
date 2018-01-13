package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.RoleVo
import com.basicfu.system.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author fuliang
 * @date 17/11/15
 */
@RestController
@RequestMapping("/role")
class RoleContoller {
    @Autowired lateinit var roleService: RoleService

    @GetMapping("/list")
    fun list(vo: RoleVo): Result {
        return Result.success(roleService.list(vo))
    }

    @GetMapping("/all")
    fun all(): Result {
        return Result.success(roleService.all())
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: RoleVo): Result {
        vo.verfiyInsert()
        roleService.insert(vo)
        return Result.insert
    }

    @PostMapping("/update")
    fun update(@RequestBody vo: RoleVo): Result {
        vo.verfiyUpdate()
        roleService.update(vo)
        return Result.update
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids:List<Long>): Result {
        roleService.delete(ids)
        return Result.delete
    }
}
