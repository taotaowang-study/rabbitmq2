package com.study.apringbootconsumer01.handler;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, //创建临时队列
            //绑定交换机
            exchange = @Exchange(name = "directExchane",type = "direct"),
            key = {"info","error","warnnig"} //路由key
    )})
    public void recive1(@Payload String msg){
        System.out.println("消费者1接收到的消息："+msg);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue, //创建临时队列
            //绑定交换机
            exchange = @Exchange(name = "directExchane",type = "direct"),
            key = {"info"} //路由key
    )})
    public void recive2(@Payload String msg){
        System.out.println("消费者2接收到的消息："+msg);
    }
}