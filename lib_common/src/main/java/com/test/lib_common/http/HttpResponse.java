package com.test.lib_common.http;
/**
 * 返回参数基类
 */
public class HttpResponse<T> {

    /**
     * msg 返回信息
     * code 返回码
     * status 返回成功状态
     * data 返回数据
     * error 返回状态
     * results 返回数据
     */

    private String msg;
    private int code;
    private int statu;
    private T data;
    public boolean error;
    public T results;
    public int start;
    public T subjects;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}
