package com.basicfu.system.common

import com.basicfu.system.model.dto.Result
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

/**
 * @author fuliang
 * @date 17/10/26
 */
@org.springframework.web.bind.annotation.ControllerAdvice
class ControllerAdvice {
    private val log = LoggerFactory.getLogger(this.javaClass)
    /**
     * 全局异常
     */
    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun errorHandler(e: Exception): Result {
        log.error(e.javaClass.name)
        log.error("全局异常--[class]--${e.javaClass}--[msg]--${e.message}")
        return Result.error(-1, e.message.toString())
    }
    /**
     * spring接收参数异常
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(e: HttpMessageNotReadableException): Result {
        log.error("${Enum.INVALID_PARAMETER.msg}-【msg】--${e.message}")
        return Result.error(Enum.INVALID_PARAMETER.value, Enum.INVALID_PARAMETER.msg)
    }

    /**
     * 自定义异常
     */
    @ResponseBody
    @ExceptionHandler(CustomException::class)
    fun customException(e: CustomException): Result {
        log.error("自定义异常--【code】--${e.code}--【msg】--${e.msg}--【data】--${e.data}")
        return Result.error(e.code,e.msg,e.data)
    }
}