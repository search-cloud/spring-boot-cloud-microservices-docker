package org.asion.bot.boot

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Asion Bot properties
 *
 * @author Asion
 */
@ConfigurationProperties(prefix = "spring.asion.bot")
class AsionBotProperties {
    /**
     * dubbo服务版本号,默认值为空
     */
    var version = ""
    /**
     * rpc服务调用超时时间
     */
    var timeout: Int? = 10000
}
