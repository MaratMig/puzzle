package com.puzzle.common;

public class ServerConfigManager {

    private static ServerConfigManager INSTANCE;
    private static int threadsNum;
    private static int port;

    private ServerConfigManager() {
    }

    public static ServerConfigManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ServerConfigManager();
        }

        return INSTANCE;
    }

    public static int getThreadsNum() {
        return threadsNum;
    }

    public static void setThreadsNum(int threadsNum) {
        ServerConfigManager.threadsNum = threadsNum;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ServerConfigManager.port = port;
    }
}
