package com.gleez.api;

/**
 * @Author Gleez
 * @Date 2020/8/4 17:35
 * 远程调用接口
 */
public interface HelloService {
    /**
     * 远程调用接口方法
     * @param helloObject 远程调用传递的参数
     * @return 远程调用的结果
     */
    String hello(HelloObject helloObject) ;
}
