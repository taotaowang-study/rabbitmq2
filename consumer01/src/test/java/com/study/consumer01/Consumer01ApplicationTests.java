package com.study.consumer01;

import com.rabbitmq.client.*;
import com.study.common.util.RabbitmqUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class Consumer01ApplicationTests {

    @Test
    void getMsg() throws Exception {

        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定队列（需要与生产者对应）
        channel.queueDeclare("hello", false, false, false, null);
        /**
         * 消费消息
         * basicConsume("hello",true,null)
         * 参数1:消费那个队列的消息，队列名
         * 参数2:是否开启消息的自动确认机制,true开启自动消息确认机制（容易丢失消息）
         * 参数3:消费时的回调接口
         */
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("接收到的消息：" + new String(body));
            }
        });
        /**
         * 关闭通道和连接，如果消费端关闭连接和通道，此时消费未处理完。
         * 虽然消息被消费，但控制台打印不出消息（不要在junit测试消费端）
         */
        //channel.close();
        //connection.close();
    }

    //-------------------- 消息确认机制
    //public static void main(String[] args) throws IOException {
    //
    //    Connection connection = RabbitmqUtil.getConnection();
    //    final Channel channel = connection.createChannel();
    //    //设置通道一次只能消费一条未确认的消息
    //    channel.basicQos(1);
    //    //通道绑定队列（需要与生产者对应）
    //    channel.queueDeclare("hello",false,false,false,null);
    //    /**
    //     * 消费消息
    //     * basicConsume("hello",true,null)
    //     * 参数1:消费那个队列的消息，队列名
    //     * 参数2:是否开启消息的自动确认机制,true开启自动消息确认机制（容易丢失消息）
    //     * 参数3:消费时的回调接口
    //     */
    //    channel.basicConsume("hello",false,new DefaultConsumer(channel){
    //        @Override
    //        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
    //                throws IOException {
    //            System.out.println("消费者1接收到的消息===>"+new String(body));
    //
    //            /**
    //             * 手段确认消费消息
    //             * basicAck(envelope.getDeliveryTag(),false)
    //             * 参数1：手动确认消息标识
    //             * 参数2：是否开启多个消息同时确认，false:不开启，每次确认一个消息
    //             */
    //            channel.basicAck(envelope.getDeliveryTag(),false);
    //        }
    //    });
    //    /**
    //     * 关闭通道和连接，如果消费端关闭连接和通道，此时消费未处理完。
    //     * 虽然消息被消费，但控制台打印不出消息（不要在junit测试消费端）
    //     */
    //    //channel.close();
    //    //connection.close();
    //}

    // ------------------fanout广播模式（发布/订阅）
    //public static void main(String[] args) throws Exception {
    //    Connection connection = RabbitmqUtil.getConnection();
    //    Channel channel = connection.createChannel();
    //    //绑定交换机或使用exchangeBind()方法
    //    channel.exchangeDeclare("ordersExchange", "fanout");
    //    //创建临时队列
    //    String queue = channel.queueDeclare().getQueue();
    //    System.out.println(queue);
    //    //将临时队列绑定exchange，参数3为路由key
    //    channel.queueBind(queue, "ordersExchange", "");
    //    //处理消息
    //    channel.basicConsume(queue, true, new DefaultConsumer(channel) {
    //        @Override
    //        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
    //                throws IOException {
    //            System.out.println("消费者1: " + new String(body));
    //        }
    //    });
    //}

    //------------------------Routing路由模式
    //public static void main(String[] args) throws Exception {
    //    Connection connection = RabbitmqUtil.getConnection();
    //    Channel channel = connection.createChannel();
    //    //绑定交换机或使用exchangeBind()方法
    //    channel.exchangeDeclare("logs_direct","direct");
    //    //创建临时队列
    //    String queue = channel.queueDeclare().getQueue();
    //    System.out.println(queue);
    //    //将临时队列绑定exchange，并指定路由key
    //    channel.queueBind(queue,"logs_direct","error");
    //    channel.queueBind(queue,"logs_direct","warnnig");
    //    channel.queueBind(queue,"logs_direct","info");
    //    //处理消息
    //    channel.basicConsume(queue,true,new DefaultConsumer(channel){
    //        @Override
    //        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
    //                throws IOException {
    //            System.out.println("消费者1: "+new String(body));
    //        }
    //    });
    //}

    public static void main(String[] args) throws Exception {
        Connection connection = RabbitmqUtil.getConnection();
        Channel channel = connection.createChannel();
        //绑定交换机或使用exchangeBind()方法
        channel.exchangeDeclare("topicsExchange","topic");
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        System.out.println(queue);
        //将临时队列绑定exchange，并设置获取交换机中动态路由key
        channel.queueBind(queue,"topicsExchange","user.*");
        //处理消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                System.out.println("消费者1: "+new String(body));
            }
        });
    }


}
