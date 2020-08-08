package com.gleez.core.loadbanlance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 轮询负载均衡
 * @Author Gleez
 * @Date 2020/8/8 13:48
 */
public class RoundRobinLoadBalance implements LoadBalance {

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        index %= instances.size();
        return instances.get(index++);
    }
}
