package com.puzzle.common;

public class ServerConfigManager {

    private static ServerConfigManager INSTANCE = new ServerConfigManager();
    private int threadsNum;
    private int port;

    private ServerConfigManager() {

    }

    public static ServerConfigManager getInstance() {
        return INSTANCE;
    }

    public int getThreadsNum() {
        return threadsNum;
    }

    public void setThreadsNum(int threadsNum) {
        this.threadsNum = threadsNum;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void init(String threads, String port) {
        try {
            setThreadsNum(Integer.valueOf(threads));
            setPort(Integer.valueOf(port));
        } catch (Exception e) {
            System.out.println("threads / port values should be numeric");
            System.exit(0);
        }

    }
}
