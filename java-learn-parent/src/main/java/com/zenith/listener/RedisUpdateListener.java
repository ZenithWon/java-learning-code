package com.zenith.listener;

import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import com.zenith.config.MQRedisUpdateConfig;
import com.zenith.constant.RedisConstant;
import com.zenith.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUpdateListener {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = MQRedisUpdateConfig.QUEUE_NAME)
    public void handlerUpdate(Message message, Channel channel){
        String msg=new String(message.getBody());
        Item item = JSONUtil.toBean(msg , Item.class);
        stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+item.getId(),msg,30, TimeUnit.SECONDS);
        log.debug("Update item: [{}]",msg);
    }
}
