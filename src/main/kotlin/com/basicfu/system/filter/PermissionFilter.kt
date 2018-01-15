package com.basicfu.system.filter

import com.alibaba.fastjson.JSON
import com.basicfu.common.util.RedisUtil
import com.basicfu.system.common.Constant
import com.basicfu.system.common.Enum
import com.basicfu.system.common.Token
import com.basicfu.system.model.dto.Result
import com.basicfu.system.model.po.Resource
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(-1)
@WebFilter(filterName = "systemPermissionFilter", urlPatterns =["/*"])
class PermissionFilter : Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse
        val uri = request.requestURI
        var result = Result.error(Enum.INVALID_PERMISSION.value, Enum.INVALID_PERMISSION.msg)
        if("/favicon.ico" == uri){
            filterChain.doFilter(servletRequest, servletResponse)
            return
        }else{
            val authorization= request.getHeader("Authorization")
            if (!authorization.isNullOrBlank()) {
                val tokenObj = RedisUtil.get(Constant.Redis.TOKEN + authorization)
                //auth存在并且redis存在无过期
                if(tokenObj!=null){
                    val token = tokenObj as Token
                    val resource = RedisUtil.hGet(Constant.Redis.RESOURCE, uri)
                    if(resource!=null){
                        val rs=resource as Resource
                        if(token.resources?.contains(rs.id) == true){
                            RedisUtil.expire(Constant.Redis.TOKEN + authorization,Constant.System.SESSIN_TIMEOUT)
                            filterChain.doFilter(servletRequest, servletResponse)
                            return
                        }
                    }
                }else{
                    //auth存在但是redis不存在，可能是auth已过期或压根不存在返回为登录超时
                    result = Result.error(Enum.LOGIN_TIMEOUT.value, Enum.LOGIN_TIMEOUT.msg)
                }
            }else{
                //访客权限控制
                val tokenObj = RedisUtil.get(Constant.Redis.TOKEN_NOT_LOGIN)
                if(tokenObj!=null){
                    val token = tokenObj as Token
                    val resource = RedisUtil.hGet(Constant.Redis.RESOURCE, uri)
                    if(resource!=null){
                        val rs=resource as Resource
                        if(token.resources?.contains(rs.id) == true){
                            filterChain.doFilter(servletRequest, servletResponse)
                            return
                        }
                    }
                }
            }
        }
        response.characterEncoding = "UTF-8"
        response.contentType = "application/json; charset=utf-8"
        response.status = HttpStatus.OK.value()
        response.outputStream.write(JSON.toJSONString(result).toByteArray())
        response.outputStream.flush()
        response.outputStream.close()
    }

    override fun destroy() {}
}
