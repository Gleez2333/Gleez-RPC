package com.gleez.core.loadbanlance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡接口
 * @Author Gleez
 * @Date 2020/8/8 13:42
 */
public interface LoadBalance {

    Instance select(List<Instance> instances);

}
