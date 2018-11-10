package io.vincent.account.router

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

/**
 * Register controller.
 *
 * @author Vincent.
 * @since 2018/11/05.
 */
@Controller
@RequestMapping("/register")
class RegisterRouterController {

    @GetMapping("/view")
    fun register(): ModelAndView = ModelAndView("account/register")

}
