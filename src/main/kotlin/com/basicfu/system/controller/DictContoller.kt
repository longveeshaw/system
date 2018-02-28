package com.basicfu.system.controller

import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.vo.DictVo
import com.basicfu.system.model.vo.RoleVo
import com.basicfu.system.service.DictService
import com.basicfu.system.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author fuliang
 * @date 18/2/28
 */
@RestController
@RequestMapping("/dict")
class DictContoller {
    @Autowired lateinit var dictService: DictService

//    @GetMapping("/list")
//    fun list(vo: RoleVo): Result {
//        return Result.success(roleService.list(vo))
//    }
//
    @GetMapping("/all")
    fun all(): Result {
        return Result.success(dictService.all())
    }

    @PostMapping("/insert")
    fun insert(@RequestBody vo: DictVo): Result {
        vo.verfiyInsert()
        dictService.insert(vo)
        return Result.insert
    }

//    @PostMapping("/update")
//    fun update(@RequestBody vo: RoleVo): Result {
//        vo.verfiyUpdate()
//        roleService.update(vo)
//        return Result.update
//    }
//
//    @DeleteMapping("/delete")
//    fun delete(@RequestBody ids:List<Long>): Result {
//        roleService.delete(ids)
//        return Result.delete
//    }
}
