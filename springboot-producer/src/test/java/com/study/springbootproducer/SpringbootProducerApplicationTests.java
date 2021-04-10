package com.study.springbootproducer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootProducerApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
        /**
         * 发送消息
         * convertAndSend(队列名,发送的消息)
         */
        rabbitTemplate.convertAndSend("hello1", "hello world");
    }

    @Test
    void testWork() {
        /**
         * 发送消息
         * convertAndSend(队列名,发送的消息)
         */
        for (int i = 1; i <= 10; i++) {
            rabbitTemplate.convertAndSend("workQueue","工作队列消息"+i);
        }
    }

    @Test
    public void testFanout()  {
        rabbitTemplate.convertAndSend("logExchane","","这是日志广播");
    }

    @Test
    public void testRouting()  {
        String routingKey="error";
        rabbitTemplate.convertAndSend("directExchane",routingKey,"这是路由key为"+routingKey+"的信息");
    }

    @Test
    public void testTopics(){
        String routingKey="user.save.findAll";
        rabbitTemplate.convertAndSend("topicExchange",routingKey,routingKey+"的消息");
    }
}
