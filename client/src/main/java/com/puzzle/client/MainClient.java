package com.puzzle.client;

import com.puzzle.client.utils.ClientConfigManager;

public class MainClient {

    public static void main(String[] args) throws Exception {

        System.out.println("Please note that ip and port values can be override by System property");

        String ip = System.getProperty("ip", "127.0.0.1");
        System.out.println("Client ip: " + ip);
        String port = System.getProperty("port", "7095");
        System.out.println("Client port: " + port);

        ClientConfigManager conf = ClientConfigManager.getInstance();
        conf.setInputPath("C:\\Git_work\\puzzle\\puzzle\\client\\src\\main\\resources\\inputFiles");
        conf.setIp(ip);
        try {
            conf.setPort(Integer.valueOf(port));
        } catch (Exception e) {
            System.out.println("port value should be numeric");
            System.exit(0);
        }

        ClientExecutor clientExecutor = new ClientExecutor();
        clientExecutor.startGames(conf.getInputPath());
    }


}