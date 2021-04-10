package com.study.apringbootconsumer01.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

public class TestMessageConverter implements MessageConverter {


    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        System.out.println("=======toMessage=========");
        return new Message(object.toString().getBytes(),messageProperties);
    }

    //消息类型转换器中fromMessage方法返回的类型就是消费端处理器接收的类型
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        System.out.println("=======fromMessage=========");
        return new String(message.getBody());
    }
}
