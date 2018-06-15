package com.puzzle.server;

import com.puzzle.server.utils.ServerConfigManager;

public class MainServer {

    public static void main(String[] args) throws Exception {

        ServerConfigManager configManager = new ServerConfigManager();
        configManager.setThreadsNum(1);
        configManager.setPort(7000);
        ServerExecutor serverExecutor = new ServerExecutor();
        serverExecutor.startSever(configManager.getThreadsNum(), configManager.getPort());
    }


}