package com.puzzle.client.utils;

public class ClientConfigManager {

    private static ClientConfigManager INSTANCE;
    private static String inputPath;
    private static String outputPath;
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

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String outputPath) {
        ClientConfigManager.outputPath = outputPath;
    }

    public void init(String ip, String port, String inputPath, String outputPath) {

        setInputPath(inputPath);
        setOutputPath(outputPath);
        setIp(ip);
        try {
            setPort(Integer.valueOf(port));
        } catch (Exception e) {
            System.out.println("port value should be numeric");
            System.exit(0);
        }

    }
}
