package com.tairui.smartcommunity.tenant.model;

/**
 * Created by zhanghu on 25/10/2016.
 */

public class HttpResult<T> {
    private int code = 0;
    private String msg = "";
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
