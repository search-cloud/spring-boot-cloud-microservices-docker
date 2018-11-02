package io.vincent.common.utils

import java.util.regex.Pattern
import kotlin.experimental.and

/**
 * 正则校验工具类
 *
 * @author hxz
 * @since 2016/08/06 11:09
 */
object RegexUtils {

    private fun startCheck(reg: String, string: String): Boolean {
        val tem: Boolean

        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(string)

        tem = matcher.matches()
        return tem
    }


    /**
     * 手机号码验证,11位，没有验证详细的手机号码段，只是验证开头必须是1和位数
     */
    fun checkMobile(mobile: String): Boolean {
        val reg = "^[1][\\d]{10}"
        return startCheck(reg, mobile)
    }

    /**
     * 检查EMAIL地址(同时校验后缀)
     * 用户名和网站名称必须>=1位字符
     * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾
     */
    fun checkEmailWithSuffix(email: String): Boolean {
        val regex = "\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)"
        return startCheck(regex, email)
    }

    /**
     * 检查EMAIL地址
     * 用户名和网站名称必须>=1位字符
     * 地址结尾必须是2位以上，如：cn,test,com,info
     */
    fun checkEmail(email: String): Boolean {
        val regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"

        return startCheck(regex, email)
    }

    /**
     * 校验emoji 字符
     *
     * @param content 内容
     * @return true | false
     */
    fun checkEmoji(content: String): Boolean {
        val p = Pattern.compile("[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]|[\u2700-\u27BF]")
        val m = p.matcher(content)
        while (m.find()) {
            return false
        }

        return true
    }

    /**
     * 校验mysql默认支持UTF8字符
     *
     * @param content 内容
     * @return true | false
     */
    fun checkMySqlDefaultSupportCharacter(content: String): Boolean {
        val bytes = content.toByteArray()

        for (byte in bytes) {
            if (byte.and(0xF8.toByte()) == 0xF0.toByte()) {
                //一个字符4个字节,MySQL 默认 UTF8 编码 不支持
                return false
            }
        }

        return true
    }

    /**
     * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
     */
    fun checkPostcode(postCode: String): Boolean {
        val regex = "^[1-9]\\d{5}"
        return startCheck(regex, postCode)
    }

    /**
     * 校验密码 支持英文字母、数字和字符
     */
    fun checkPassword(passowrd: String, min: Int, max: Int): Boolean {
        val regex = "^[\\x20-\\x7e]{$min,$max}$"
        return startCheck(regex, passowrd)
    }

    /**
     * 检验用户名
     * 支持中文,英文,数字,'_'的组合
     * 必须以中文或英文字母开头
     * 不区分大小写
     * 一个汉字为两个字符
     * 用户名有最小长度和最大长度限制，比如用户名必须是6-16位
     */
    fun checkYtxUsername(username: String, min: Int, max: Int): Boolean {
        var chinese: Int = 0
        val p = Pattern.compile("[\u4e00-\u9fa5]")
        val m = p.matcher(username)
        while (m.find()) {
            chinese++
        }

        val integerMin = if (min - chinese - 1 >= 0) min - chinese - 1 else 0
        val integerMax = if (max - chinese - 1 >= 0) max - chinese - 1 else max

        val regex = "^[A-Za-z\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5_]{$integerMin,$integerMax}$"
        return !(username.length + chinese > max || username.length + chinese < min) && startCheck(regex, username)
    }

    /**
     * 检验用户名首字符
     * 必须以中文或英文字母开头
     * 不区分大小写
     */
    fun checkYtxUsernamePrefix(username: String): Boolean {
        val regex = "^[A-Za-z\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5_]{*}$"
        return startCheck(regex, username)
    }

    /**
     * 检验用户名
     * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾
     * 有最小位数限制的用户名，比如：用户名最少为4位字符
     */
    fun checkUsername(username: String, min: Int): Boolean {
        //[\\w\u4e00-\u9fa5]{2,}?
        val regex = "[\\w\u4e00-\u9fa5]{$min,}(?<!_)"

        return startCheck(regex, username)
    }

    /**
     * 检验用户名
     * 取值范围为a-z,A-Z,0-9,"_",汉字
     * 最少一位字符，最大字符位数无限制，不能以"_"结尾
     */
    fun checkUsername(username: String): Boolean {
        val regex = "[\\w\u4e00-\u9fa5]+(?<!_)"
        return startCheck(regex, username)
    }

    /**
     * 查看IP地址是否合法
     */
    fun checkIP(ipAddress: String): Boolean {
        val regex = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
                "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"

        return startCheck(regex, ipAddress)
    }

    /**
     * 验证国内电话号码
     * 格式：010-67676767，区号长度3-4位，必须以"0"开头，号码是7-8位
     */
    fun checkPhone(phone: String): Boolean {
        val regex = "^[0]\\d{2,3}\\-?\\d{7,8}"

        return startCheck(regex, phone)
    }

    /**
     * 验证国内电话号码
     * 格式：6767676, 号码位数必须是7-8位,头一位不能是"0"
     */
    fun checkPhoneWithoutCode(phone: String): Boolean {
        val reg = "^[1-9]\\d{6,7}"

        return startCheck(reg, phone)
    }

    /**
     * 验证国内电话号码
     * 格式：0106767676，共11位或者12位，必须是0开头
     */
    fun checkPhoneNrWithoutLine(phoneNr: String): Boolean {
        val reg = "^[0]\\d{10,11}"

        return startCheck(reg, phoneNr)
    }

    /**
     * 验证国内身份证号码：15或18位，由数字组成，不能以0开头
     */
    fun checkIdCard(idNr: String): Boolean {
        //        String reg = "^[1-9](\\d{14}|\\d{17})";
        val reg = "(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)"
        return startCheck(reg, idNr)
    }

    /**
     * 网址验证<br></br>
     * 符合类型：<br></br>
     * http://www.test.com<br></br>
     * http://163.com
     */
    fun checkWebSite(url: String): Boolean {
        //http://www.163.com
        val reg = "^(http)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)"

        return startCheck(reg, url)
    }
}
