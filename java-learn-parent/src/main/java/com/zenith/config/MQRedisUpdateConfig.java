package com.zenith.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQRedisUpdateConfig {
    public static final String EXCHANGE_NAME="redis.update.exchange";
    public static final String QUEUE_NAME="redis.update.queue";
    public static final String ROUTING_KEY="redis.update";

    @Bean(EXCHANGE_NAME)
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_NAME,true,false);
    }

    @Bean(QUEUE_NAME)
    public Queue queue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean(ROUTING_KEY)
    public Binding binding(@Qualifier(EXCHANGE_NAME) DirectExchange exchange,
                           @Qualifier(QUEUE_NAME) Queue queue){
        return  BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
