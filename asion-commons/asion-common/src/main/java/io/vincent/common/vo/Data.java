package io.vincent.common.vo;

import java.util.Map;

/**
 * @author Asion.
 * @since 2017/10/13.
 */
public interface Data {

    Map<String, Object> getData();

    <T> T getData(String dataName);

    <T> T addData(String dataName, T dataValue);

    <T> Map<String, T> addAllData(Map<String, T> data);
}
