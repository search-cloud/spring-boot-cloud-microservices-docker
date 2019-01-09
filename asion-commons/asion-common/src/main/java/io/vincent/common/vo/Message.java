package io.vincent.common.vo;

/**
 * 与客户端交互时，返回的信息
 *
 * @author Asion.
 * @since 2017/10/13.
 */
public interface Message {

    /**
     * 返回处理成功与否
     * @return "true" or "false"
     */
    String getSucceed();

    /**
     * 设置成功与否
     *
     * @param succeed 成功与否
     */
    void setSucceed(String succeed);

    /**
     * 返回信息的统一代码
     *
     * @return code 统一代码
     */
    String getCode();

    /**
     * 设置信息的统一代码
     *
     * @param code 信息的统一代码
     */
    void setCode(String code);

    /**
     * 返回具体处理信息
     *
     * @return 具体处理信息
     */
    String getMessage();

    /**
     * 设置具体信息
     *
     * @param message 具体处理信息
     */
    void setMessage(String message);
}
