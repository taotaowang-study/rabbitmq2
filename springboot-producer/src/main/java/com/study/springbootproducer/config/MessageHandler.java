package com.study.springbootproducer.config;

public class MessageHandler {


    public void onMessage(String message){
        System.out.println("---------onMessage-------------");
        System.out.println(message);
    }
}