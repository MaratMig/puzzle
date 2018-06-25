package com.puzzle.server;

import com.puzzle.common.ServerConfigManager;

public class MainServer {

    public static void main(String[] args) throws Exception {

        System.out.println("Please note that threads and port values can be override by System property");
        System.out.println("e.g. -Dthreads=2 , -Dport=7000");
        String threads = System.getProperty("threads", "4");
        System.out.println("Server current num of threads: " + threads);
        String port = System.getProperty("port", "7095");
        System.out.println("Server current port: " + port);

        ServerConfigManager configManager = ServerConfigManager.getInstance();
        configManager.init(threads, port);
        ServerExecutor serverExecutor = new ServerExecutor();
        serverExecutor.startServer(configManager.getThreadsNum(), configManager.getPort());
    }


}