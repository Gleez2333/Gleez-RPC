package com.gleez.server;

import com.gleez.api.HelloObject;
import com.gleez.api.HelloService;
import com.gleez.core.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author Gleez
 * @Date 2020/8/4 17:39
 * 远程调用端口实现类
 */
@Service
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject helloObject) {
        logger.info("HelloService接收到：{}", helloObject.getMessage());
        return "这是HelloService调用的返回值:" + helloObject.getId();
    }
}
