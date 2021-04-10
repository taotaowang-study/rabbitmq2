package com.study.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.study.common.util.RabbitmqUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class ProducerApplicationTests {

    @Test
    void sendMsg() throws IOException, TimeoutException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 通道绑定对应的消息队列
         * queueDeclare("队列名",是否持久化队列,是否独占队列,自动删除,其他参数)
         * 队列名：如果队列不存在会自动创建队列
         * 是否持久化队列：如果没有持久化队列，那么在rabbitmq服务重新启动时会删除已有的队列。队列中的消息数据也会消失（duiable）,
         *     注意：如果队列持久化了，rabbitmq服务重新启动时队列不会被删除，但消息会丢失。需要下面设置
         * 是否独占队列：如果为true，该队列只允许当前连接通道可用绑定，其他连接通道不可绑定
         *              为false。允许其他连接通道可以绑定
         * 自动删除：是否在消费完成后自动删除队列，如果为true，在消费端与队列断开连接后自动删除队列
         */
        channel.queueDeclare("hello", false, false, false, null);
        /**
         * 发布消息
         * basicPublish(交换机,队列名,传递消息额外设置,消息内容)
         * channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,"这是          *                                                      我发布的消息".getBytes());
         * MessageProperties.PERSISTENT_TEXT_PLAIN：将队列中的消息持久化到磁盘
         */
        for (int i = 1; i <= 10; i++) {
            channel.basicPublish("", "hello", null, "这是我发布的消息".getBytes());
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    @Test
    public void sendMsg2() throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 将通道声明指定的交换机（如果不存在自动创建）
         * exchangeDeclare("ordersExchange","fanout",false)
         * 参数1：交换机名称
         * 参数2：交换机类型，fanout表示广播模式（固定）
         * 参数3：是否持久化
         */
        channel.exchangeDeclare("ordersExchange","fanout",false);
        //channel.basicPublish(交换机名称,路由key,是否持久化消息,要发送的消息);
        channel.basicPublish("ordersExchange","",null,"发布的消息".getBytes());
        RabbitmqUtil.close(connection,channel);
    }

    @Test
    public void sendMsg3() throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明交换机iii
         * exchangeDeclare("交换机名称","交换机类型",是否持久化)
         */
        channel.exchangeDeclare("logs_direct","direct",false);
        //路由key
        String routingkey="error";
        //发布消息
        channel.basicPublish("logs_direct",routingkey,null,("指定的route key:["+routingkey+"]的消息").getBytes());
        RabbitmqUtil.close(connection,channel);
    }

    @Test
    public void sendMsg4() throws IOException {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明交换机
         * exchangeDeclare("交换机名称","交换机类型")
         */
        channel.exchangeDeclare("topicsExchange","topic");
        //路由key
        String routingkey="user.save.alex";
        //发布消息
        channel.basicPublish("topicsExchange",routingkey,null,("这是路由中的动态订阅模型,route key: ["+routingkey+"]").getBytes());
        RabbitmqUtil.close(connection,channel);
    }



}
