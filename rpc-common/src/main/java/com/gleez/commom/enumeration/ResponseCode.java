package com.gleez.commom.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Gleez
 * @Date 2020/8/4 18:23
 * 响应转态码和转态信息
 */
@AllArgsConstructor
@Getter
public enum  ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(400, "未找到指定方法"),
    CLASS_NOT_FOUND(401, "未找到指定类");

    private final int code;
    private final String message;
}
