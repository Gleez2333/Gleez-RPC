package com.gleez.api.impl;

import com.gleez.api.ByeService;
import com.gleez.core.annotation.Service;

/**
 * @Author Gleez
 * @Date 2020/8/9 10:23
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String msg) {
        return "Bye, " + msg;
    }
}
