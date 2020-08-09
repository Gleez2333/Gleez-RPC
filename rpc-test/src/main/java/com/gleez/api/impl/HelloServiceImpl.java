package com.gleez.api.impl;

import com.gleez.api.HelloService;
import com.gleez.core.annotation.Service;


/**
 * @Author Gleez
 * @Date 2020/8/4 17:39
 * 远程调用端口实现类
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        return "Hello, " + msg;
    }
}
