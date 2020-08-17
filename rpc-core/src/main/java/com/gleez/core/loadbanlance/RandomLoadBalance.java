package com.gleez.core.loadbanlance;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 * @Author Gleez
 * @Date 2020/8/8 13:46
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public Object select(List<?> instances) {
        if(instances == null) return null;
        return instances.get(new Random().nextInt(instances.size()));
    }
}
