package org.asion.sample.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sample dubbo rpc service properties
 *
 * @author Asion.
 * @since 16/5/29.
 */
@ConfigurationProperties(prefix = "spring.asion.sample")
public class AsionSampleProperties {

    /**
     * dubbo服务版本号,默认值为空
     */
    private String version = "";

    /**
     * rpc服务调用超时时间
     */
    private Integer timeout = 10000;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
