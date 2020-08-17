package com.gleez.commom.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Gleez
 * @Date 2020/8/17 12:47
 */
@AllArgsConstructor
@Getter
public enum RegistryType {

    ZOOKEEPER(0),
    NACOS(1);
    private final int code;
}
