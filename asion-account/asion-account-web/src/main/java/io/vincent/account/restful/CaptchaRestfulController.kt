package io.vincent.account.restful

import com.google.code.kaptcha.impl.DefaultKaptcha
import io.vincent.account.util.CaptchaHelper
import io.vincent.account.util.IPUtil
import io.vincent.common.vo.RestResult
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



/**
 * Captcha Restful controller.
 *
 * @author Vincent.
 * @since 2018/11/05.
 */
@RestController
@RequestMapping("/captcha")
class CaptchaRestfulController @Autowired
constructor(private val captchaProducer: DefaultKaptcha, private val captchaHelper: CaptchaHelper) {

    /**
     * Get captcha.
     *
     * @param response httpResp.
     * @return Response.
     */
    @GetMapping
    @ResponseBody
    fun getCaptcha(response: HttpServletResponse): RestResult {

        response.setDateHeader("Expires", 0)
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate")
        response.addHeader("Cache-Control", "post-check=0, pre-check=0")
        response.setHeader("Pragma", "no-cache")
        response.contentType = "image/png"

        val captcha = captchaProducer.createText()
        // cache captcha
        captchaHelper.cacheCaptcha(captcha, response)

        val captchaImg = captchaProducer.createImage(captcha)

        return RestResult.succeed().data("captchaBase64", CaptchaHelper.encodeBase64(captchaImg)).build()
    }

    /**
     * Validate captcha.
     *
     * @param captcha  captcha need to validate.
     * @param cid      uuid.
     * @param request  httpReq.
     * @param response httpResp.
     * @return validate result.
     */
    @PostMapping("/validate")
    @ResponseBody
    fun validateCaptcha(@RequestParam("captcha") captcha: String,
                        @CookieValue(value = "cid", required = false) cid: String,
                        request: HttpServletRequest, response: HttpServletResponse): RestResult {

        if (logger.isDebugEnabled) {
            logger.debug("Client: captcha={}, captchaUuid={}", captcha, cid)
        }

        return if (captcha == "") {
            RestResult.failed().build()
        } else captchaHelper.validateCaptcha(null, captcha, false, IPUtil.getIpAddr(request), cid, false, response)

    }

    companion object {
        private val logger = LoggerFactory.getLogger(CaptchaRestfulController::class.java)
    }
}
