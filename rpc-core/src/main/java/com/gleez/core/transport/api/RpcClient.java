package com.gleez.core.transport.api;

import com.gleez.commom.entity.RpcRequest;
import com.gleez.core.loadbanlance.LoadBalance;
import com.gleez.core.serializer.CommonSerializer;

/**
 * @Author Gleez
 * @Date 2020/8/5 9:45
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);

    void setLoadBalance(LoadBalance loadBalance);
}
