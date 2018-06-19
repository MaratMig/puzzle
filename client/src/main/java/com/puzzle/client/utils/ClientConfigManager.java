package com.puzzle.client.utils;

public class ClientConfigManager {

    private static ClientConfigManager INSTANCE;
    private static String inputPath;
    private static String ip;
    private static int port;

    private ClientConfigManager() {
    }

    public static ClientConfigManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ClientConfigManager();
        }

        return INSTANCE;
    }

    public static String getInputPath() {
        return inputPath;
    }

    public static void setInputPath(String inputPath) {
        ClientConfigManager.inputPath = inputPath;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        ClientConfigManager.ip = ip;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        ClientConfigManager.port = port;
    }
}
