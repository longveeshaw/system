package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.PermissionVo
import com.basicfu.system.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author fuliang
 * @date 17/10/31
 */
@RestController
@RequestMapping("/permission")
class PermissionContoller {
    @Autowired lateinit var permissionService: PermissionService

    @GetMapping("/list")
    fun list(vo: PermissionVo): Result {
        return Result.success(permissionService.list(vo))
    }

    @GetMapping("/all")
    fun all(): Result {
        return Result.success(permissionService.all())
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: PermissionVo): Result {
        vo.verfiyInsert()
        permissionService.insert(vo)
        return Result.insert
    }

    @PostMapping("/update")
    fun update(@RequestBody vo: PermissionVo): Result {
        vo.verfiyUpdate()
        permissionService.update(vo)
        return Result.update
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids:List<Long>): Result {
        permissionService.delete(ids)
        return Result.delete
    }
}