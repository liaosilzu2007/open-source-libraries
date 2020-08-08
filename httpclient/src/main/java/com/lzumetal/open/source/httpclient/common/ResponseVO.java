package com.lzumetal.open.source.httpclient.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liaosi
 * @date 2020-08-08
 */
@Getter
@Setter
public class ResponseVO<T extends Serializable> implements Serializable {

    private int status;

    private String message;

    private T data;

    public ResponseVO() {
    }

    private ResponseVO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T extends Serializable> ResponseVO data(T data) {
        return new ResponseVO(200, "请求成功", data);
    }

}
