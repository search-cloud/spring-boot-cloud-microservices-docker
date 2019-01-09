package io.vincent.account.restful

import com.alibaba.dubbo.config.annotation.Reference
import io.vincent.account.domain.application.AccountManager
import io.vincent.account.domain.model.Account
import io.vincent.common.vo.RestResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


/**
 * Login Restful controller.
 *
 * @author Vincent.
 * @since 2018/11/05.
 */
@RestController
@RequestMapping("/")
class LoginRestfulController {

    @Reference(version = "1.0.0", application = "\${dubbo.application.id}", url = "dubbo://192.168.10.94:9907")
    private val accountManager: AccountManager? = null

    /**
     * 登录
     *
     * @param account 账号信息
     * @return 成功信息
     */
    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun login(@RequestParam username: String,
              @RequestParam password: String,
              @RequestParam(required = false) redirect: String,
              @RequestParam(required = false) captcha: String,
              @CookieValue(value = "cid", required = false) cid: String): RestResult {

        val savedAccount = accountManager!!.findByUsename(username)

        return RestResult.builder()
                .data("account", savedAccount)
                .code("A0020200")
                .message("Successfully Register").build()
    }

    /**
     * 注销
     *
     * @param account 账号信息
     * @return 成功信息
     */
    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun logout(account: Account): RestResult {
        val savedAccount = accountManager!!.create(account)
        return RestResult.builder()
                .data("account", savedAccount)
                .code("A0020200")
                .message("Successfully Register").build()
    }

}
