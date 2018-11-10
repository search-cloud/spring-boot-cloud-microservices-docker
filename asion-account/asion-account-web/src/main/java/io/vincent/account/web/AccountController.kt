package io.vincent.account.web

import com.alibaba.dubbo.config.annotation.Reference
import io.vincent.account.domain.application.AccountManager
import io.vincent.account.domain.model.Account
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.util.*
import javax.validation.Valid

/**
 * user controller.
 *
 * @author Asion.
 * @since 16/5/29.
 */
@Controller
@RequestMapping("/account")
class AccountController {

    @Reference(version = "1.0.0", application = "\${dubbo.application.id}", url = "dubbo://dubbo.asion.org:2008")
    private val accountManager: AccountManager? = null

    @RequestMapping("/list")
    fun list(): ModelAndView {
        val accounts = accountManager!!.findPage(1, 10)
        val list = accounts.list
        return ModelAndView("account/list", "accounts", list)
    }

    @RequestMapping("/{id}")
    fun view(@PathVariable("id") id: Long?): ModelAndView {
        val account = accountManager!!.findOne(id!!)
        return ModelAndView("account/view", "account", account!!)
    }

    @RequestMapping(value = "/", params = arrayOf("form"), method = arrayOf(RequestMethod.GET))
    fun createForm(@ModelAttribute account: Account): String {
        return "account/form"
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    fun create(@Valid account: Account, result: BindingResult, redirect: RedirectAttributes): ModelAndView {
        var account = account
        if (result.hasErrors()) {
            return ModelAndView("account/form", "formErrors", result.allErrors)
        }
        account = accountManager!!.create(account)
        redirect.addFlashAttribute("globalAccount", "Successfully save a new user")
        return ModelAndView("redirect:/account/{account.id}", "account.id", account.id!!)
    }

    @RequestMapping("/foo")
    fun foo(): String {
        throw RuntimeException("Expected exception in controller")
    }

    @RequestMapping(value = "/delete/{id}")
    fun delete(@PathVariable("id") id: Long?): ModelAndView {
        //        accountManager.delete(id);
        val accounts = ArrayList<Account>()
        return ModelAndView("account/list", "accounts", accounts)
    }

    @RequestMapping(value = "/modify/{id}", method = arrayOf(RequestMethod.GET))
    fun modifyForm(@PathVariable("id") id: Long?): ModelAndView {
        val account = accountManager!!.findOne(id!!)
        return ModelAndView("account/form", "account", account!!)
    }

}
