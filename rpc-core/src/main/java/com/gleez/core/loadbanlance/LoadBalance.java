package com.gleez.core.loadbanlance;

import java.util.List;

/**
 * 负载均衡接口
 * @Author Gleez
 * @Date 2020/8/8 13:42
 */
public interface LoadBalance {

    Object select(List<?> instances);

}
