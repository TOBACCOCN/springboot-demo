package com.springboot.example;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.net.SimpleSocketServer;

public class MySimpleSocketServer extends SimpleSocketServer {

    public MySimpleSocketServer(LoggerContext lc, int port) {
        super(lc, port);
    }

    public static void main(String[] args) throws Exception {
        String[] argv = new String[2];
        argv[0] = "9999";
        argv[1] = "C:\\Users\\TOBACCO\\Desktop\\server.xml";    // lookup resources/server.xml
        doMain(SimpleSocketServer.class, argv);
    }

}
