package com.xinlindeyu.vo;

import java.io.Serializable;

/**
 * 返回页面的基础bean，提供统一返回标准
 * 
 * @author znn
 *
 */
public class ResultBean<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3649363290407304698L;

    /**
     * result结果
     */
    public static final String SUCCESS = "1";

    /**
     * result结果
     */
    public static final String FAILURE = "0";

    /**
     * 返回结果
     */
    private String result;

    private String message;
    /**
     * 错误消息
     */
    private String errMsg;

    /**
     * 错误编码
     */
    private String errCode;

    /**
     * 返回结果
     */
    private T data;

    /**
     * 构造一个默认成功的结果
     */
    public ResultBean() {
        this.result = SUCCESS;
    }

    /**
     * 构造一个设置了data返回值的成功的结果
     * 
     * @param data
     */
    public ResultBean(T data) {
        this.result = SUCCESS;
        this.data = data;
    }

    /**
     * 构造错误结果，设置错误消息和错误结果
     * 
     * @param errorCode 错误编码
     * @param errorMsg 错误消息
     */
    public ResultBean(String errorCode, String errorMsg) {
        this.fail(errorCode, errorMsg);
    }

    public void fail(String errorCode, String errorMsg) {
        this.result = FAILURE;
        this.errCode = errorCode;
        this.errMsg = errorMsg;
    }

    public void fail(String errorMsg) {
        this.result = FAILURE;
        this.errMsg = errorMsg;
    }
    public Boolean isSuccess(){
        if(this.getResult().equals(SUCCESS)){
            return true;
        }else{
            return false;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean [result=" + result + ", message=" + message + ", errMsg=" + errMsg + ", errCode=" + errCode + ", data=" + data + "]";
    }

}
