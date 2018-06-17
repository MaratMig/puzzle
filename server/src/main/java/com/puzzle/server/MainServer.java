package com.puzzle.server;

import com.puzzle.utils.ServerConfigManager;

public class MainServer {

    public static void main(String[] args) throws Exception {

        String threads = System.getProperty("threads", "4");
        System.out.println("Server Num of threads: " + threads);
        String port = System.getProperty("port", "7095");
        System.out.println("Server port: " + port);

        ServerConfigManager configManager = new ServerConfigManager();
        configManager.setThreadsNum(Integer.valueOf(threads));
        configManager.setPort(Integer.valueOf(port));
        ServerExecutor serverExecutor = new ServerExecutor();
        serverExecutor.startSever(configManager.getThreadsNum(), configManager.getPort());
    }


}