package org.asion.bot.selenium.proxy

import com.alibaba.fastjson.JSON
import org.asion.bot.helper.RedisClientLocal
import org.asion.bot.selenium.HostAndPort
import kotlin.math.roundToInt

/**
 *
 * @author Asion.
 * @since 2018/5/17.
 */
class ProxyBuilder {
    companion object {
        const val proxyFlag = true
        private val hostAndPort = RedisClientLocal.get("proxyIpList")
        private val hostAndPorts = JSON.parseArray(hostAndPort, HostAndPort::class.java)

        fun getHostAndPort(): HostAndPort? {
            if (proxyFlag) {
                val index = (Math.random() * (hostAndPorts.size - 1)).roundToInt()
                return if (index > hostAndPorts.size - 1 || index < 0) {
                    null
                } else {
                    hostAndPorts[index]
                }
            }
            return null
        }

        @JvmStatic
        fun main(args: Array<String>) {
            for (i in 0..9) {
                println(getHostAndPort())
            }
        }
    }
}