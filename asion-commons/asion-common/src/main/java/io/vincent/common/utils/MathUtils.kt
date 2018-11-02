package io.vincent.common.utils

import java.math.BigDecimal
import java.util.regex.Pattern

/**
 * 数值处理工具类。
 *
 * @author Asion
 */
object MathUtils {

    private const val integerReg = "(^[0-9]+[.][0]+$)||(^[0-9]+$)"
    private const val doubleReg = "(^[0-9]+[.][0-9]+$)||(^[0-9]+$)"

    /**
     * 两数相乘
     *
     * @param first 第一个数值
     * @param second 第二个数值
     * @return 相乘的结果
     */
    fun multiply(first: Number?, second: Number?): Double {
        if (first == null || second == null) {
            return 0.0
        }
        val val1 = BigDecimal(first.toString())
        val val2 = BigDecimal(second.toString())

        return val1.multiply(val2).toDouble()
    }

    /**
     * 两数相除, 四舍五入
     *
     * @param value   除数
     * @param divisor 被除数
     * @param len     保留的小数位
     * @return 相除的结果
     */
    @JvmOverloads
    fun divide(value: Number?, divisor: Number?, len: Int = 2): Double {
        if (value == null || divisor == null) {
            return 0.0
        }

        val val1 = BigDecimal(value.toString())
        val val2 = BigDecimal(divisor.toString())

        if (val2.toDouble() == 0.0) {
            throw IllegalArgumentException("divisor is null or zero")
        }

        return val1.divide(val2, len, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 根据factor因子结合两个Double数字，factor为 1 则两数相加，为 -1 则两数相减
     *
     * @param first  第一个Double数值
     * @param second 第二个Double数值
     * @param factor 1 或 -1；
     * @return 相加或者相减的结果
     */
    fun mix(first: Double?, second: Double?, factor: Int): Double {
        var first = first
        var second = second
        if (Math.abs(factor) != 1) {
            throw IllegalArgumentException("错误的因子！factor只能为1或-1")
        }

        first = first ?: 0.0
        second = second ?: 0.0
        return add(first, second * factor)
    }

    /**
     * 两个Double型加法运算
     *
     * @param first  第一个Double数值
     * @param second 第二个Double数值
     * @return 相加的结果
     */
    fun add(first: Double?, second: Double?): Double {
        var sum = BigDecimal.ZERO
        if (first != null) {
            sum = BigDecimal.valueOf(first)
        }
        if (second != null) {
            sum = sum.add(BigDecimal.valueOf(second))
        }
        return sum.toDouble()
    }

    /**
     * 两个Double型减法运算
     *
     * @param first  第一个Double数值
     * @param second 第二个Double数值
     * @return 相减的结果
     */
    fun sub(first: Double?, second: Double?): Double {
        var first = first
        var second = second
        if (first == null) {
            first = 0.0
        }
        if (second == null) {
            second = 0.0
        }
        return add(first, second * -1)
    }

    /**
     * 正数变负数, 负数变正数
     *
     * @param number Double数值
     * @return 正数变负数, 负数变正数的结果, 如果number == null, 则返回0.
     */
    fun reverse(number: Double?): Double? {
        return if (number == null) {
            0.0
        } else -number
    }

    /**
     * 取绝对值
     *
     * @param value Double数值
     * @return 绝对值
     */
    fun abs(value: Double?): Double {
        return if (value == null) {
            0.0
        } else Math.abs(value)
    }

    /**
     * 判断是匹配传人的正则
     *
     * @param str 传入的字符串
     * @param reg 正则
     * @return 匹配返回true, 否则返回false
     */
    fun `is`(str: String?, reg: String): Boolean {
        if (null == str) return false
        val pattern = Pattern.compile(reg)
        return pattern.matcher(str).matches()
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false 1或1.0的“整数”
     */
    fun isInteger(str: String?): Boolean {
        return null != str && `is`(str, integerReg)
    }

    /**
     * 判断是否为double
     *
     * @param str 传入的字符串
     * @return 是double返回true,否则返回false 1.0或1.22的“double”
     */
    fun isDouble(str: String?): Boolean {
        return null != str && `is`(str, doubleReg)
    }

}
/**
 * 两数相除, 四舍五入, 默认保留2位小数
 *
 * @param value   除数
 * @param divisor 被除数
 * @return 相除的结果
 */