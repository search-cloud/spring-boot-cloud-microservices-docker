package io.vincent.common.vo;

import java.util.Map;

/**
 * 返回客户端的 Data 抽象.
 *
 * @author Asion.
 * @since 2017/10/13.
 */
public interface Data {

    /**
     * 获取 data map.
     *
     * @return map.
     */
    Map<String, Object> getData();

    /**
     * 获取 data map.
     *
     * @param key 数据键.
     * @param <T> 数据类型.
     * @return data.
     */
    <T> T getData(String key);

    /**
     * 添加数据.
     *
     * @param key 数据键.
     * @param value 数据值.
     * @param <T> 数据类型.
     * @return data.
     */
    <T> T data(String key, T value);

    <T> Map<String, T> data(Map<String, T> data);
}
