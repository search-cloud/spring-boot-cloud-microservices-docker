package io.vincent.common.constant

/**
 * 移动端统一错误返回文案
 *
 *
 *
 * @author Vincent
 * @since 2017/6/23
 */
object AppErrorMessage {
    //通用错误返回
    val COMMON_ERROR = "请求失败"
    val COMMON_SUBMIT_ERROR = "提交失败"
    val COMMON_RETRY_LATER = "请稍后再试"

    //账户错误返回
    val ACCOUNT_DISABLE = "账号已被冻结"
    val ACCOUNT_STOPPED = "账号已停用"
    val ACCOUNT_ABNORMAL = "账号状态异常"
    val ACCOUNT_NON_EXIST = "账号不存在"

    //手机验证码错误返回
    val SMS_CODE_ERROR = "短信验证码不正确"
    val SMS_CODE_EXPIRED = "短信验证码已过期"
    val SMS_CODE_INVALID = "短信验证码已失效，请重新获取"
    val SMS_MOBILE_FREQUENT_1_MINUTE = "手机号请求频繁,请1分钟后再试"
    val SMS_MOBILE_FREQUENT_30_MINUTE = "手机号请求频繁,请30分钟后再试"
    val SMS_MOBILE_FREQUENT_24_HOUR = "手机号请求频繁,请24小时后再试"
    val SMS_IP_FREQUENT_30_MINUTE = "IP请求频繁,请30分钟后再试"
    val SMS_IP_FREQUENT_24_HOUR = "IP请求频繁,请24小时后再试"


    //图形验证码错误返回
    val CAPTCHA_ERROR = "字符验证码不正确，请重新验证"
    val CAPTCHA_EXPIRED = "字符验证码已过期，请重新验证"
    //    String CAPTCHA_NEEDED_FOR_MOBILE_FREQUENT = "手机号请求频繁，请先验证";
    //    String CAPTCHA_NEEDED_FOR_LOGIN_FREQUENT = "登录请求频繁，请先验证";
    val CAPTCHA_NEEDED_FOR_MOBILE_FREQUENT = "请输入字符验证码" // 移动端暂时登陆请求频繁不需要提示信息
    val CAPTCHA_NEEDED_FOR_LOGIN_FREQUENT = ""


    //密码错误返回
    val PASSWORD_ERROR = "密码不正确"//用于登录用户输入密码做其他操作
    val PASSWORD_NON_LEGAL = "密码必须是6-20位英文字母、数字或字符"

    //手机号错误返回
    val MOBILE_FORMAT_ERROR = "手机号格式不正确"
    val MOBILE_BOUND = "手机号已被绑定"
    val MOBILE_REGISTERED = "手机号已注册"
    val MOBILE_NON_REGISTER = "手机号未注册"

    //用户名错误返回
    val NAME_EXISTED = "用户名已存在"
    val NAME_UNUSABLE = "用户名不可用"
    val NAME_CANNOT_MODIFY = "不支持修改用户名"
    val NAME_NON_LEGAL = "用户名4-16个字符，一个汉字2个字符，支持中英文、数字、“_”的组合，须中英文开头"

    //昵称错误返回
    val NICK_NAME_EXISTED = "昵称已存在"

    //登陆错误返回
    val LOGIN_NAME_PASSWORD_ERROR = "用户名或密码不正确"//用于未登录情况

}
