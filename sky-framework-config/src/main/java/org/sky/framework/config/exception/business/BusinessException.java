package org.sky.framework.config.exception.business;

/**
 * @author yangcong
 *
 * 业务异常
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BusinessException() {}
}
