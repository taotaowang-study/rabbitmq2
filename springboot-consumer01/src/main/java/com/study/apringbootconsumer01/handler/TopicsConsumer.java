package com.study.apringbootconsumer01.handler;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TopicsConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.*", "user.save", "product.#"},
                    exchange = @Exchange(type = "topic", name = "topicExchange")
            )
    })
    public void receive1(@Payload String msg) {
        System.out.println("消费者1接收到的消息：" + msg);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    key = {"user.#"},
                    exchange = @Exchange(type = "topic", name = "topicExchange")
            )
    })
    public void receive2(@Payload String msg) {
        System.out.println("消费者2接收到的消息：" + msg);
    }
}