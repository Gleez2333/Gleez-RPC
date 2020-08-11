package com.gleez.core.transport.api;

import com.gleez.commom.entity.RpcRequest;

/**
 * RPC客户端接口
 * @Author Gleez
 * @Date 2020/8/5 9:45
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

}
