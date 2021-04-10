package com.study.apringbootconsumer01.config;

public class MessageHandler {


    public void onMessage(String message){
        System.out.println("---------onMessage-------------");
        System.out.println(message);
    }
}