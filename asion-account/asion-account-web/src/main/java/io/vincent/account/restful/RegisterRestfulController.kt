package io.vincent.account.restful

import com.alibaba.dubbo.config.annotation.Reference
import io.vincent.account.domain.application.AccountManager
import io.vincent.account.domain.model.Account
import io.vincent.common.vo.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

/**
 * Register Restful controller.
 *
 * @author Vincent.
 * @since 2018/11/05.
 */
@RestController
@RequestMapping("/register")
class RegisterRestfulController {

    @Reference(version = "1.0.0", application = "\${dubbo.application.id}", url = "dubbo://dubbo.asion.org:2008")
    private val accountManager: AccountManager? = null

    /**
     * 提交注册
     *
     * @param account 账号信息
     * @return 成功信息
     */
    @PutMapping("/reg")
    fun create(@Valid account: Account): ResponseEntity<Response> {
        val savedAccount = accountManager!!.create(account)
        val response = Response.builder()
                .addData("account", savedAccount)
                .code("A0020200")
                .message("Successfully Register").build()
        return ResponseEntity(response, HttpStatus.OK)
    }

}
