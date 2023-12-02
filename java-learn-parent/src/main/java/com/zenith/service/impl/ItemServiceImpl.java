package com.zenith.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zenith.common.R;
import com.zenith.constant.RedisConstant;
import com.zenith.entity.Item;
import com.zenith.entity.RedisData;
import com.zenith.mapper.ItemMapper;
import com.zenith.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public R generateItemData(Integer num) {
        itemMapper.delete(new LambdaQueryWrapper<Item>().le(Item::getId,10000L));

        for(int i=1;i<=num;i++){
            Item item = new Item();
            item.setName(UUID.randomUUID().toString());
            item.setId(Long.parseLong(String.valueOf(i)));
            itemMapper.insert(item);
        }

        return R.ok();
    }

    @Override
    public R generateLogicalTTLItemData() {
        itemMapper.deleteById(99999L);

        Item item = new Item();
        item.setId(99999L);
        item.setName(UUID.randomUUID().toString());
        itemMapper.insert(item);

        RedisData redisData = new RedisData();
        redisData.setData(item);
        redisData.setDdlTime(System.currentTimeMillis()+ TimeUnit.SECONDS.toMillis(1));
        stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+item.getId(), JSON.toJSONString(redisData));

        return R.ok();
    }
}
