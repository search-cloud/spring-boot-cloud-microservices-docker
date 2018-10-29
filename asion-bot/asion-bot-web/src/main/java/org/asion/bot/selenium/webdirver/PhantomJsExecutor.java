package org.asion.bot.selenium.webdirver;


import org.asion.bot.selenium.HostAndPort;

import java.io.*;

/**
 * @author Asion.
 * @since 2018/5/17.
 */
public class PhantomJsExecutor {

    public static String execute(String url, HostAndPort hostAndPort) {

        String PHANTOMJS_EXECUTABLE_PATH = "/Users/Asion/Workstation/Tools/phantomjs-2.1.1-macosx/bin/phantomjs";
        String PHANTOMJS_GHOSTDRIVER_PATH = "/Users/Asion/Workstation/Personal/java-workspace/ytx-seeker/ytx-seeker-web/src/main/resources/templates/loadspeed.js";

        try {
            /*获取cmd命令*/
            // 虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。

            String command = PHANTOMJS_EXECUTABLE_PATH + " " + PHANTOMJS_GHOSTDRIVER_PATH + " " + url;
            if (hostAndPort != null) {
                command += " --proxy=" + hostAndPort.getIp() + ":" + hostAndPort.getPort();
            }

            System.out.println("command: " + command);

            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = //"https://channel.jd.com/1316-1381.html";
                "https://list.tmall.com/search_product.htm?spm=875.7931836/B.subpannel2016034.2.6e4a4265AjGoJ9&pos=2&cat=50029231&acm=201603078.1003.2.1311817&scm=1003.2.201603078.OTHER_1485205911278_1311817";
        // "https://www.tmall.com/";//"https://www.baidu.com/";
        // "https://list.tmall.com/search_product.htm?spm=a220m.1000858.1000722.26.4b1d2324QvyPRc&cat=50031544&prop=21299:27772&sort=d&style=l&search_condition=7&from=sn_1_prop&active=1&industryCatId=50029231&smAreaId=330100#J_crumbs"
        System.out.println("url: " + url);
        String html = execute(url, null);
        System.out.println(html);
    }
}
