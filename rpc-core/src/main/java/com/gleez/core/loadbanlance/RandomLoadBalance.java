package com.gleez.core.loadbanlance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 * @Author Gleez
 * @Date 2020/8/8 13:46
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public Instance select(List<Instance> instances) {
        if(instances == null) return null;
        return instances.get(new Random().nextInt(instances.size()));
    }
}
