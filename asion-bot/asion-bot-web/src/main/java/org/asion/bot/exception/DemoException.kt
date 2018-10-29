package org.asion.bot.exception

import com.ytx.common.exception.YtxBizException
import com.ytx.common.exception.YtxCommonException
import com.ytx.common.vo.ClientMessage
import java.util.*

/**
 * @author Asion.
 * @since 16/12/6.
 */
class DemoException : YtxBizException {

    /**
     * 构造业务异常
     *
     * @param code    按规范定义的错误码
     * @param message 错误或异常消息
     */
    constructor(code: String, message: String) : super(code, message)

    /**
     * 构造业务异常
     *
     * @param code    按规范定义的错误码
     * @param message 错误或异常消息
     * @param values  需要替换的{} 按顺序
     */
    constructor(code: String, message: String, vararg values: String) : super(code, message, *values)

    /**
     * 构造业务异常
     *
     * @param code    按规范定义的错误码
     * @param message 错误或异常消息
     * @param map     动态替换的内容
     */
    constructor(code: String, message: String, map: Map<*, *>) : super(code, message, map)

    /**
     * 使用ClientMessage列表构造异常
     *
     * @param errorList 异常信息。
     */
    constructor(errorList: List<ClientMessage>) : super(errorList)

    companion object {
        /**
         * bundle FQN
         */
        protected val BUNDLE_FQN = "i18n.YtxDemoErrors"

        /**
         * init resource bundle
         */
        init {
            YtxCommonException.resourceBundle = ResourceBundle.getBundle(BUNDLE_FQN, YtxCommonException.locale)
        }
    }

}
