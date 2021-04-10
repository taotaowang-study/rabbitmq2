package com.study.apringbootconsumer01.handler;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {
    /**
     * 消费者1
     */
    @RabbitListener(bindings = {@QueueBinding(
                //绑定交换机（没有则创建）
                exchange=@Exchange(name = "logExchane",type = "fanout") ,
                value = @Queue//创建临时队列

    )})
    public void recive1(@Payload String msg){
        System.out.println("消费者1接收到的消息："+msg);
    }

    /**
     * 消费者2
     */
    @RabbitListener(bindings = {@QueueBinding(
            //绑定交换机（没有则创建）
            exchange=@Exchange(name = "logExchane",type = "fanout") ,
            value = @Queue//创建临时队列

    )})
    public void recive2(@Payload String msg){
        System.out.println("消费者2接收到的消息："+msg);
    }
}