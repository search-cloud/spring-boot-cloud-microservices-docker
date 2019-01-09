package io.vincent.common.vo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 与客户端交互的返回结果
 *
 * @author Asion.
 * @since 2017/10/13.
 */
public class RestResult implements Message, Data, Serializable {

    private static final String DEFAULT_SUCCEED_CODE = "V00-000200";
    private static final String DEFAULT_FAILED_CODE = "E00-000500";
    private static final String DEFAULT_SUCCEED_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAILED_MESSAGE = "FAIL";

    /**
     * 操作成功与否 由后台返回前台
     */
    private String succeed = SuccessFlag.TRUE.value;

    /**
     * 处理信息代码
     */
    private String code = DEFAULT_SUCCEED_CODE;

    /**
     * 信息
     */
    private String message = DEFAULT_SUCCEED_MESSAGE;

    /**
     * 返回客户端所带的数据
     */
    private Map<String, Object> data = new LinkedHashMap<>();

    public static Builder succeed() {
        return RestResult.builder().succeed(SuccessFlag.TRUE.value).code(DEFAULT_SUCCEED_CODE).message(DEFAULT_SUCCEED_MESSAGE);
    }

    public static Builder failed() {
        return RestResult.builder().succeed(SuccessFlag.FALSE.value).code(DEFAULT_FAILED_CODE).message(DEFAULT_FAILED_MESSAGE);
    }

    @Override
    public String getSucceed() {
        return succeed;
    }

    public void setSuccessFalse() {
        this.succeed = SuccessFlag.FALSE.value;
    }

    public void setSuccessTrue() {
        this.succeed = SuccessFlag.TRUE.value;
    }

    /**
     * 操作是不是成功
     *
     * @return true：成功, false：未成功
     */
    public Boolean isSucceed() {
        return SuccessFlag.TRUE.code == 1 && SuccessFlag.TRUE.value.equals(succeed.toLowerCase());
    }

    /**
     * 操作是不是失败
     *
     * @return true：失败, false：无误
     */
    public Boolean isFailed() {
        return SuccessFlag.FALSE.code == 0 && SuccessFlag.FALSE.value.equals(succeed.toLowerCase());
    }

    @Override
    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the underlying {@code data} instance (never {@code null}).
     */
    @Override
    public Map<String, Object> getData() {
        if (this.data == null) {
            this.data = new LinkedHashMap<>();
        }
        return this.data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        if (this.data == null) {
            this.data = new LinkedHashMap<>();
        }
        return (T) this.data.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T data(String key, T value) {
        if (key == null) {
            throw new IllegalArgumentException("Model attribute name must not be null");
        }
        if (this.data == null) {
            this.data = new LinkedHashMap<>();
        }
        this.data.put(key, value);
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> data(Map<String, T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Model attribute must not be null");
        }
        if (this.data == null) {
            this.data = new LinkedHashMap<>(data.size());
        }
        this.data.putAll(data);
        return data;
    }

    public enum SuccessFlag {

        TRUE(1, "true"), FALSE(0, "false");

        private int code;
        private String value;

        SuccessFlag(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    public RestResult() {
        //默认为true
        setSuccessTrue();
    }

    public RestResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(String code, String message, Map<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private RestResult(String succeed, String code, String message, Map<String, Object> data) {
        this.succeed = succeed;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        /**
         * 操作成功与否 由后台返回前台
         */
        private String succeed = SuccessFlag.TRUE.value;

        /**
         * 处理信息代码
         */
        private String code = DEFAULT_SUCCEED_CODE;

        /**
         * 信息
         */
        private String message = DEFAULT_SUCCEED_MESSAGE;

        /**
         * 返回客户端所带的数据
         */
        private Map<String, Object> data = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder succeed(String succeed) {
            this.succeed = succeed;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Map<String, Object> data) {
            if (this.data == null) {
                this.data = new LinkedHashMap<>();
            }
            if (data == null) {
                throw new IllegalArgumentException("Model dataMap must not be null");
            }
            this.data.putAll(data);
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> Builder data(String dataName, T dataValue) {
            if (this.data == null) {
                this.data = new LinkedHashMap<>();
            }
            if (dataName == null) {
                throw new IllegalArgumentException("Model attribute name must not be null");
            }
            this.data.put(dataName, dataValue);
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "succeed='" + succeed + '\'' +
                    ", code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }

        public RestResult build() {
            return new RestResult(succeed, code, message, data);
        }
    }
}
