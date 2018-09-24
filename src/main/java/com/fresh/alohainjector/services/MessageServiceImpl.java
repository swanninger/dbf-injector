package com.fresh.alohainjector.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void sendCmdMsg(String msg) {
        String user = System.getProperty("user.name");
        try {
            Runtime.getRuntime().exec("msg " + user + " " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
