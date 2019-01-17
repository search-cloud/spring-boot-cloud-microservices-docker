package io.vincent.account.restful

import com.alibaba.dubbo.config.annotation.Reference
import io.vincent.account.domain.application.AccountManager
import io.vincent.account.domain.model.Account
import io.vincent.common.utils.StringUtils
import io.vincent.common.vo.RestResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*



/**
 * Register Restful controller.
 *
 * @author Vincent.
 * @since 2018/11/05.
 */
@RestController
@RequestMapping("/accounts/signup")
class RegisterRestfulController {

    @Reference(version = "1.0.0", application = "\${dubbo.application.id}", url = "dubbo://192.168.10.94:9907")
    private val accountManager: AccountManager? = null

    /**
     * 手机快速注册.
     *
     * @param account 账号信息.
     * @return 成功与否信息.
     */
    @PostMapping("/{mobile}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@PathVariable("mobile") mobile: String, account: Account): RestResult {
        // TODO 检验
        val savedAccount = accountManager!!.create(account)
        return RestResult.builder().data("account", savedAccount)
                .code("A0020200").message("Successfully Register").build()
    }

    /**
     * 用户名与邮箱注册.
     *
     * @param account 账号信息.
     * @return 成功与否信息.
     */
    @PostMapping("/")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUsername(account: Account): RestResult {
        //
        val savedAccount = accountManager!!.create(account)
        return RestResult.builder().data("account", savedAccount)
                .code("A0020200").message("Successfully Register").build()
    }

    /**
     * 普通验证码.
     *
     * @return 成功信息
     */
    @GetMapping("/captcha")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun normalCaptcha(@RequestParam("mobile", required = false) mobile: String?): RestResult {
        // 普通验证码
        val captcha = "123456"
        return RestResult.builder().data("captcha", captcha).data("mobile", mobile).build()
    }

    /**
     * 手机短信验证码.
     *
     * @return 成功信息.
     */
    @GetMapping("/{mobile}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun mobileCaptcha(@PathVariable("mobile") mobile: String,
                      @RequestParam("normalCaptcha") normalCaptcha: String): RestResult {
        // 验证手机是否
        val captcha = "123456"
        return RestResult.builder().data("captcha", captcha).data("mobile", mobile).build()
    }

    /**
     * 验证手机是否已经注册.
     *
     * @param mobile 手机号码.
     * @return 返回是否注册信息.
     */
    @RequestMapping("/{mobile}/validate")
    @ResponseBody
    fun checkMobileIsExist(@PathVariable("mobile") mobile: String): RestResult {
        val result = RestResult.failed()
        if (!StringUtils.isMobile(mobile)) {
            result.code("403")
            result.message("mobile invalid!")
            return result.build()
        }
        val account = accountManager!!.findByMobile(mobile)
        if (account != null) {
            result.code("405")
            result.message("mobile already register!")
            return result.build()
        }

        return RestResult.succeed().data("valid", true).build()
    }

}
