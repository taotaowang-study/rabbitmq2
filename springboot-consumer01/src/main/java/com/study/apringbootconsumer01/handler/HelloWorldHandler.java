package com.study.apringbootconsumer01.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: HelloWorldHandler
 * @DESCRIPTION:
 * @author: 西门
 * @create: 2021-04-10 18:58
 **/
@Component
//@RabbitListener(queuesToDeclare = @Queue("hello1"))
public class HelloWorldHandler {

    //@RabbitHandler
    //public void recive(String msg) {
    //    System.out.println("接收到的消息：" + msg);
    //}

    /**
     * @RabbitListener(queues = ("hello"))绑定已有队列
     * @Payload 用于获取队列中的body消息
     * @Headers 用于获取所有header头部信息
     * @Header 用于获取单个header头信息
     * Channel 管道对象，可设置ack应答
     */
    //@RabbitListener(queues = ("hello1"))
    @RabbitListener(queuesToDeclare = @Queue("hello1"))
    public void get(@Payload String msg,
                    @Headers Map<String,Object> headers,
                    @Header(AmqpHeaders.DELIVERY_TAG) long tag,//ack参数
                    Message message,
                    Channel channel){
        System.out.println("接收到的消息："+msg);
        System.out.println("接收到的消息："+headers.toString());
        System.out.println("接收到的消息："+channel);
        System.out.println("接收到的消息："+message.toString());
        //channel.basicAck(tag,false);
    }

    /**
     * 消费者1
     */
    @RabbitListener(queuesToDeclare =@Queue(name = "workQueue"))
    public void recive1(@Payload String msg){
        System.out.println("消费者1接收到的消息："+msg);
    }

    /**
     * 消费者2
     */
    @RabbitListener(queuesToDeclare =@Queue(name = "workQueue"))
    public void recive2(@Payload String msg){
        System.out.println("消费者2接收到的消息："+msg);
    }


}
