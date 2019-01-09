package io.vincent.account.util;

import io.vincent.common.utils.StringUtils;
import io.vincent.common.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Captcha helper.
 *
 * @author Vincent.
 * @since 2019/01/08.
 */
@Component
public class CaptchaHelper {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${account.domain}")
    private String domain;

    private final static String CAPTCHA_UUID = "cid";

    private final static int EXPIRE_TIME = 3 * 60;

    @Value("${account.captcha.need}")
    private boolean needCaptcha = true; // 暂时：账户登录必须要验证码

    @Value("${account.captcha.user-need}")
    private boolean needCaptchaByUserName = false;

    @Value("${account.captcha.mobile-need}")
    private boolean needCaptchaByMobile = false;

    @Autowired
    public CaptchaHelper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 验证字符验证码.
     *
     * TODO save mobile captcha needed value.
     *
     * @param mobile             手机号
     * @param captcha            字符验证码
     * @param ip                 请求IP 地址
     * @param isCaptchaForceNeed 是否强制检测需要验证码。 特殊情况：登陆注册必须要验证码。此时直接校验验证码是否正确
     * @param captchaUuid        字符验证码uuid
     * @param expire             是否失效
     * @param response           httpResponse
     * @return 检验结果.
     */
    public RestResult validateCaptcha(String mobile, String captcha, boolean isCaptchaForceNeed, String ip, String captchaUuid, Boolean expire, HttpServletResponse response) {
        // 强制需要校验验证码isForceNeedCaptcha. 不强制时才检验下面的接口
        if (isCaptchaForceNeed || needCaptchaByMobile) {
            return doValidateCaptcha(captcha, captchaUuid, expire, response);
        }
        // 不需验证码, 直接返回
        return RestResult.succeed().build();
    }

    /**
     * 根据用户名验证字符验证码(登录使用).
     *
     * TODO save username captcha needed value.
     *
     * @param username    用户名(手机号)
     * @param captcha     字符验证码
     * @param captchaUuid 字符验证码uuid
     * @param response    httpResponse
     * @return 检验结果.
     */
    public RestResult validateCaptchaByUserName(String username, String captcha, String ip, String captchaUuid, Boolean expire, HttpServletResponse response) {
        if (needCaptcha) {
            if (StringUtils.isBlank(captcha)) {
                // 验证码为空 E99-100500
                return RestResult.failed()
                               .code("E99-100501").message("captcha could not be null!")
                               .data("captchaNeed", true).build();
            }
        }

        if (needCaptchaByUserName) {
            if (StringUtils.isBlank(captcha)) {
                // 验证码为空 E99-100500
                return RestResult.failed()
                               .code("E99-100501").message("captcha could not be null!")
                               .data("captchaNeedByUserName", true).build();
            }
        }

        if (needCaptcha) {
            return doValidateCaptcha(captcha, captchaUuid, expire, response);
        }

        // 不需验证码, 直接返回
        return RestResult.succeed().build();
    }

    /**
     * 缓存验证码.
     *
     * @param captcha  验证码.
     * @param response httpResp.
     */
    public void cacheCaptcha(String captcha, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        //设置redis 缓存
        redisTemplate.opsForValue().set(uuid, captcha, EXPIRE_TIME, TimeUnit.SECONDS);
        //设置cookie
        Cookie cookie = new Cookie(CAPTCHA_UUID, uuid);
        cookie.setPath("/");
        cookie.setMaxAge(EXPIRE_TIME);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }


    /**
     * 检验验证码.
     *
     * @param captcha     字符验证码
     * @param captchaUuid 字符验证码uuid
     * @param expired     是否过期
     * @param response    httpResponse
     * @return 检验结果.
     */
    private RestResult doValidateCaptcha(String captcha, String captchaUuid, Boolean expired, HttpServletResponse response) {
        if (StringUtils.isBlank(captcha)) {
            // 验证码为空 E99-100500
            return RestResult.failed()
                           .code("E99-100501").message("captcha could not be null!")
                           .data("captchaNeed", true).build();
        }

        if (StringUtils.isBlank(captchaUuid)) {
            return RestResult.failed()
                           .code("E99-100502").message("captcha has expired!")
                           .data("captchaExpired", true).build();
        }

        Object realCaptchaObject = redisTemplate.opsForValue().get(captchaUuid);

        if (realCaptchaObject == null) {
            return RestResult.failed()
                           .code("E99-100502").message("captcha has expired!")
                           .data("captchaExpired", true).build();
        }

        if (expired) {
            doExpireCaptcha(captchaUuid, response);
        }

        if (captcha.equalsIgnoreCase(realCaptchaObject.toString())) {
            return RestResult.succeed().build();
        }

        return RestResult.failed()
                       .code("E99-100500").message("captcha error!")
                       .data("captchaError", true).build();
    }

    /**
     * 删除 redis key, 置失效
     *
     * @param uuid     captcha 对应的key
     * @param response response
     */
    private void doExpireCaptcha(String uuid, HttpServletResponse response) {
        Cookie cookie = new Cookie(CAPTCHA_UUID, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain(domain);
        response.addCookie(cookie);
        redisTemplate.delete(Collections.singleton(uuid));
    }

}
