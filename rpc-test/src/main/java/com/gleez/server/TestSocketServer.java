package com.gleez.server;

import com.gleez.core.annotation.ServiceScan;
import com.gleez.core.transport.socket.SocketServer;

/**
 * @Author Gleez
 * @Date 2020/8/4 19:55
 */
@ServiceScan
public class TestSocketServer {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer("127.0.0.1", 9999);
        socketServer.start();
    }
}
