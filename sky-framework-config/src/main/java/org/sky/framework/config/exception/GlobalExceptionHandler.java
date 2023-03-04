package org.sky.framework.config.exception;

import org.sky.framework.api.common.result.AjaxResult;
import org.sky.framework.config.exception.business.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangcong
 *
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     * @param businessException
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Object business(BusinessException businessException, HttpServletRequest request){
        log.error("业务异常 ===>>> %s , 业务类型 %s", businessException.getMessage(), businessException.getClass().getName());
        return AjaxResult.error(500, null, businessException.getMessage());
    }

}
