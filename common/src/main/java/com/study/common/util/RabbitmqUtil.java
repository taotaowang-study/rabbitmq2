package com.study.common.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: RabbitmqUtil
 * @DESCRIPTION: rabbitmq工具类
 * @author: 西门
 * @create: 2021-04-10 15:29
 **/
public class RabbitmqUtil {

    /**
     * @param
     * @description: 获取rabbitmq连接
     * @return: com.rabbitmq.client.Connection
     * @author: wtt
     * @date: 2021-04-10 15:34
     */
    public static Connection getConnection() {
        //创建连接rabbitmq的连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置主机地址
        factory.setHost("47.99.107.252");
        //设置端口
        factory.setPort(5672);
        //设置要连接的的虚拟主机
        factory.setVirtualHost("/");
        factory.setUsername("tao");
        factory.setPassword("tao");
        //从工厂获取连接对象
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection, Channel channel) {
        //关闭通道和连接
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
