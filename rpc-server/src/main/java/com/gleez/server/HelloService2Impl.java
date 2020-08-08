package com.gleez.server;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService2;
import com.gleez.core.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Gleez
 * @Date 2020/8/5 9:11
 */
@Service
public class HelloService2Impl implements HelloService2 {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello2(HelloObject helloObject) {
        logger.info("HelloService2接收到：{}", helloObject.getMessage());
        return "这是HelloService2调用的返回值:" + helloObject.getId();
    }
}
