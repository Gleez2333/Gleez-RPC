package com.gleez.commom.entity;

import com.gleez.commom.enumeration.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Gleez
 * @Date 2020/8/4 18:12
 * 远程调用结果格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应转态描述
     */
    private String message;

    /**
     * 响应结果
     */
    private T data;

    public static <T> RpcResponse<T> success(T data, ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setData(data);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }


}
