package com.puzzle.server;

import com.puzzle.common.ServerConfigManager;

public class MainServer {

    public static void main(String[] args) throws Exception {

        String threads = System.getProperty("threads", "4");
        System.out.println("Server Num of threads: " + threads);
        String port = System.getProperty("port", "7095");
        System.out.println("Server port: " + port);

        ServerConfigManager configManager = ServerConfigManager.getInstance();
        try {
            configManager.setThreadsNum(Integer.valueOf(threads));
            configManager.setPort(Integer.valueOf(port));
        } catch (Exception e) {
            System.out.println("threads / port values should be numeric");
            System.exit(0);
        }
        ServerExecutor serverExecutor = new ServerExecutor();
        serverExecutor.startSever(configManager.getThreadsNum(), configManager.getPort());
    }


}