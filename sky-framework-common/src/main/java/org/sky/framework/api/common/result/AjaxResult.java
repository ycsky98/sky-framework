package org.sky.framework.api.common.result;

/**
 * @author yangcong
 *
 * 统一返回信息
 */
public class AjaxResult {

    private Integer code;

    private Object data;

    public String message;

    public AjaxResult(){}

    public AjaxResult(Integer code, Object data, String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public AjaxResult(Object data){
        this.code = 200;
        this.data = data;
        this.message = "SUCCESS";
    }

    public static AjaxResult success(Object data){
        return new AjaxResult(data);
    }

    public static AjaxResult error(Integer code, Object data, String message){
        return new AjaxResult(code, data, message);
    }
}
