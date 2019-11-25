package com.github.chenmingq.server.all;

import com.github.chenmingq.common.common.annotation.ScanMapping;
import com.github.chenmingq.server.system.Server;

/**
 * @author: chenmingqin
 * date: 2019/11/21 23:34
 * description:
 */

@ScanMapping(name = "com.github.chenmingq.server.system.controller")
public class ServerStart {
    public static void main(String[] args) {
        Server.startServer(ServerStart.class, args);
    }
}
