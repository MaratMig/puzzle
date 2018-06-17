package com.puzzle.utils;

public class ServerConfigManager {

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

    private int threadsNum;
    private int port;

    public ServerConfigManager() {
    }



}
