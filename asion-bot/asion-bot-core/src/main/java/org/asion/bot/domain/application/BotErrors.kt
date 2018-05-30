//package org.asion.bot.domain.application
//
//import org.jetbrains.annotations.NonNls
//import org.jetbrains.annotations.PropertyKey
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Component
//
//import java.text.MessageFormat
//import java.util.Locale
//import java.util.MissingResourceException
//import java.util.ResourceBundle
//
///**
// * @author Asion
// * @since 15/11/17.
// */
//@Component
//class BotErrors {
//
//    @Autowired(required = false)
//    private val counterService: CounterService? = null
//
//    /**
//     * 获取resource bundle中对应的bundle值
//     *
//     * @param key    bundle key
//     * @param params 参数，bundle的值采用MessageFormat的格式化方式
//     * @return bundle值，如果bundle key不存在，返回特定key丢失格式
//     */
//    fun message(@PropertyKey(resourceBundle = BUNDLE_FQN) key: String, vararg params: Any): String {
//        var value: String
//        try {
//            value = ourBundle.getString(key)
//            counterService?.increment("errors." + key)
//        } catch (ignore: MissingResourceException) {
//            value = "!!!$key!!!"
//        }
//
//        if (params.isNotEmpty() && value.indexOf('{') >= 0) {
//            value = MessageFormat.format(value, *params)
//        }
//        return key + ": " + value
//    }
//
//    companion object {
//
//        /**
//         * bundle FQN
//         */
//        @NonNls
//        private const val BUNDLE_FQN = "i18n.YtxDemoErrors"
//        /**
//         * resource bundle
//         */
//        private val ourBundle = ResourceBundle.getBundle(BUNDLE_FQN, Locale("en", "US"))
//    }
//}
