package org.asion.bot.selenium.processor

import us.codecraft.webmagic.Request

/**
 *
 * @author Asion.
 * @since 2018/5/7.
 */
class RequestExtend(url: String, var parentUrl: String?) : Request(url)