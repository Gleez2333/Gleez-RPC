package com.gleez.core.loadbanlance;

import java.util.List;

/**
 * 轮询负载均衡
 * @Author Gleez
 * @Date 2020/8/8 13:48
 */
public class RoundRobinLoadBalance implements LoadBalance {

    private int index = 0;

    @Override
    public Object select(List<?> instances) {
        index %= instances.size();
        return instances.get(index++);
    }
}
