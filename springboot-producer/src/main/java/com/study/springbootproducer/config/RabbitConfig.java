package com.study.springbootproducer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitConfig
 * @DESCRIPTION:
 * @author: 西门
 * @create: 2021-04-10 16:37
 **/
@Slf4j
@Configuration
@RabbitListener
public class RabbitConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause) ->{
            String messageId = correlationData.getId();
            if(ack){
                log.info("交换机确认收到消息,messageId={}",messageId);
            }else{
                log.error("交换机消息确认失败");
            }
        });
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息从交换机路由到队列失败,exchange:{},routeKey:{},replyCode:{},replyText:{},message:{}", exchange,
                    routingKey, replyCode, replyText, message);
        });
        return rabbitTemplate;
    }
}
