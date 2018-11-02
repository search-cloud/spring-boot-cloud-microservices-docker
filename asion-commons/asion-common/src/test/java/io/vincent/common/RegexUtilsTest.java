package io.vincent.common;

import io.vincent.common.utils.RegexUtils;
import org.junit.Test;

/**
 * @author hxz
 * @since 2016/08/19 15:24
 */
public class RegexUtilsTest {
    @Test
    public void testCheckYtxUserName(){
        String name = "一二三四五六";
        Boolean result = RegexUtils.INSTANCE.checkYtxUsername(name,6,16);
        if(result) {
            System.out.println("符合迎天下用户名规则");
        }else {
            System.out.println("不符合迎天下用户名规则");
        }
    }

    @Test
    public void testCheckYtxEmoji(){
//        String content = "Thats a nice joke  \uD83D\uDE06\uD83D\uDE06\uD83D\uDE06 \uD83D\uDE1B";
        String content = "Thats a nice joke - ~ ` @#￥%……&*——+$()";
        Boolean result = RegexUtils.INSTANCE.checkEmoji(content);
        if(result) {
            System.out.println("符合规则");
        }else {
            System.out.println("不符合规则");
        }
    }

    @Test
    public void testCheckMySqlDefaultSupportCharacter(){
        String content = "Thats a nice joke  \uD83D\uDE06\uD83D\uDE06\uD83D\uDE06 \uD83D\uDE1B";
//        String content = "Thats a nice joke - ~ ` @#￥%……&*——+$()";
        Boolean result = RegexUtils.INSTANCE.checkMySqlDefaultSupportCharacter(content);
        if(result) {
            System.out.println("符合规则");
        }else {
            System.out.println("不符合规则");
        }
    }

    @Test
    public void checkPhone(){
        String phone = "0571-8888888";

        Boolean result = RegexUtils.INSTANCE.checkPhone(phone);
        if(result) {
            System.out.println("符合规则");
        }else {
            System.out.println("不符合规则");
        }
    }

    @Test
    public void checkPhoneOrMobile(){
//        String phone = "182681000";
        String phone = "0571-8888888";

        Boolean result = RegexUtils.INSTANCE.checkPhone(phone) || RegexUtils.INSTANCE.checkMobile(phone);
        if(result) {
            System.out.println("符合规则");
        }else {
            System.out.println("不符合规则");
        }
    }

    @Test
    public void checkPassword(){
        String password = "a@$1无@%$& sdh";

        Boolean result = RegexUtils.INSTANCE.checkPassword(password,6,20);
        if(result) {
            System.out.println("符合规则");
        }else {
            System.out.println("不符合规则");
        }
    }
}
