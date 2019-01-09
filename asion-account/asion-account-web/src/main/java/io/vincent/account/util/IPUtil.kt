package io.vincent.account.util


import io.vincent.common.utils.StringUtils

import javax.servlet.http.HttpServletRequest

/**
 * Simple IP utility.
 *
 * @author Vincent.
 * @since 2019/01/08
 */
object IPUtil {
    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request 请求
     * @return IP地址
     */
    @JvmStatic
    fun getIpAddr(request: HttpServletRequest): String? {
        var ip = request.getHeader("X-Real-IP")
        if (StringUtils.isNotBlank(ip) && !"unknown".equals(ip, ignoreCase = true)) {
            return ip
        }
        ip = request.getHeader("X-Forwarded-For")
        return if (StringUtils.isNotBlank(ip) && !"unknown".equals(ip, ignoreCase = true)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            val index = ip.indexOf(',')
            if (index != -1) {
                ip.substring(0, index)
            } else {
                ip
            }
        } else {
            request.remoteAddr
        }
    }
}
