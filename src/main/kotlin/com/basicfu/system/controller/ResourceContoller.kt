package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.ResourceVo
import com.basicfu.system.service.ResourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author fuliang
 * @date 17/10/30
 */
@RestController
@RequestMapping("/resource")
class ResourceContoller {
    @Autowired lateinit var resourceService: ResourceService

    @GetMapping("/list")
    fun list(vo:ResourceVo): Result {
        return Result.success(resourceService.list(vo))
    }
    @GetMapping("/all")
    fun all(): Result {
        return Result.success(resourceService.all())
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: ResourceVo): Result {
        vo.verfiyInsert()
        resourceService.insert(vo)
        return Result.insert
    }

    @PostMapping("/update")
    fun update(@RequestBody vo: ResourceVo): Result {
        vo.verfiyUpdate()
        resourceService.update(vo)
        return Result.update
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody ids:List<Long>): Result {
        resourceService.delete(ids)
        return Result.delete
    }
}
