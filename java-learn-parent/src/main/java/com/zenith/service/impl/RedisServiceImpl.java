package com.zenith.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zenith.common.R;
import com.zenith.constant.RedisConstant;
import com.zenith.entity.Item;
import com.zenith.entity.RedisData;
import com.zenith.mapper.ItemMapper;
import com.zenith.service.RedisService;
import com.zenith.utis.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    BloomFilter bloomFilter;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    RedissonClient redissonClient;

    private static final ExecutorService CACHE_REBUILD_THREAD=Executors.newSingleThreadExecutor();

    @PostConstruct
    void init(){
        log.info("Init bloom filter");
        List<Item> items = itemMapper.selectList(null);
        items.forEach(item -> {
            bloomFilter.insert(RedisConstant.DEFAULT_ITEM_PREFIX + item.getId().toString());
        });
    }

    @Override
    public R cacheThroughNullSolution(Long id) {
        String res = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString());
        if(res!=null){
            if(res.equals(RedisConstant.REDIS_NULL_VALUE)){
                return R.ok(null,"Select from redis: data is null");
            }else{
                return R.ok(JSONObject.parseObject(res),"Select from redis");
            }
        }
        Item item = itemMapper.selectById(id);
        if(item==null){
            stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id,RedisConstant.REDIS_NULL_VALUE,30, TimeUnit.SECONDS);
            return R.ok(null,"Select from mysql: data is null");
        }else{
            stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id, JSON.toJSONString(item),30, TimeUnit.SECONDS);
            return R.ok(item,"Select from mysql");
        }

    }

    @Override
    public R cacheThroughBloomSolution(Long id) {
        //NOTE: Before use bloom filter, you must remember to init it with data in mysql
        if(!bloomFilter.select(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString())){
            return R.ok(null,"Select from bloom filter: data is null");
        }

        String res = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString());
        if(res!=null){
            return R.ok(JSONObject.parseObject(res),"Select from redis");
        }

        Item item = itemMapper.selectById(id);
        if(item==null){
            stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id,RedisConstant.REDIS_NULL_VALUE,30, TimeUnit.SECONDS);
            return R.ok(null,"Select from mysql: data is null, bloom filter failed");
        }else{
            stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id, JSON.toJSONString(item),30, TimeUnit.SECONDS);
            return R.ok(item,"Select from mysql");
        }
    }

    @Override
    public R hitThroughMutexSolution(Long id) {
        String json = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString());
        if(StrUtil.isNotBlank(json)){
            return R.ok(JSONObject.parseObject(json),"Select from redis");
        }

        RLock lock = redissonClient.getLock(RedisConstant.DEFAULT_ITEM_LOCK_PREFIX + id);
        try {
            boolean res = lock.tryLock(10 , TimeUnit.SECONDS);
            if(!res){
                return R.ok(null,"Cannot get lock");
            }
            json = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString());
            if(StrUtil.isNotBlank(json)){
                return R.ok(JSONObject.parseObject(json),"Select from redis");
            }

            Item item = itemMapper.selectById(id);
            Thread.sleep(1000);
            if(item==null){
                return R.ok(null,"Select from mysql: data is null");
            }else{
                stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id, JSON.toJSONString(item),30, TimeUnit.SECONDS);
                return R.ok(item,"Select from mysql");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public R hitThroughMLogicalTtlSolution(Long id) {
        String json = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id.toString());
        RedisData redisData = JSONUtil.toBean(json , RedisData.class);
        if(redisData.getDdlTime()>=System.currentTimeMillis()){
            return R.ok(redisData.getData(),"Select from redis: not expired");
        }
        RLock lock = redissonClient.getLock(RedisConstant.DEFAULT_ITEM_LOCK_PREFIX + id);

        try{
            boolean res = lock.tryLock();
            if(!res){
                return R.ok(redisData.getData(),"Select from redis: already expired");
            }
            CACHE_REBUILD_THREAD.submit(()->{
                try{
                    log.debug("Cache rebuild");
                    Item item = itemMapper.selectById(id);
                    Thread.sleep(1000);
                    RedisData redisData1=new RedisData();
                    redisData1.setData(item);
                    redisData1.setDdlTime(System.currentTimeMillis()+ TimeUnit.SECONDS.toMillis(30));
                    stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+item.getId(), JSON.toJSONString(redisData1));

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    log.debug("Cache rebuild complete");
                    stringRedisTemplate.delete(RedisConstant.DEFAULT_ITEM_LOCK_PREFIX+id);
                }
            });
            return R.ok(redisData.getData(),"Select from redis: already expired");
        }catch (Exception e){
            e.printStackTrace();
            return R.error();
        }
    }

    @Override
    public R doubleWriteLockRead(Long id) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(RedisConstant.DEFAULT_ITEM_WR_LOCK_PREFIX + id);
        try {
            boolean res = readWriteLock.readLock().tryLock(10 , TimeUnit.SECONDS);
            Thread.sleep(3000);
            if(!res){
                return R.error("Get lock failed");
            }
            String json = stringRedisTemplate.opsForValue().get(RedisConstant.DEFAULT_ITEM_PREFIX + id);
            if(StrUtil.isNotBlank(json)){
                return R.ok(JSONObject.parseObject(json),"Select from redis");
            }

            Item item = itemMapper.selectById(id);
            if(item==null){
                return R.ok(null,"Select from mysql: data not exist");
            }

            stringRedisTemplate.opsForValue().set(RedisConstant.DEFAULT_ITEM_PREFIX+id,JSON.toJSONString(item),30,TimeUnit.SECONDS);
            return R.ok(item,"Select from mysql");

        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }finally {
            readWriteLock.readLock().unlock();
        }

    }

    @Override
    public R doubleWriteLockWrite(Long id) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(RedisConstant.DEFAULT_ITEM_WR_LOCK_PREFIX + id);
        try {
            boolean res = readWriteLock.writeLock().tryLock(10 , TimeUnit.SECONDS);
            if(!res){
                return R.error("Get lock fail");
            }
            Thread.sleep(3000);
            itemMapper.update(null,new LambdaUpdateWrapper<Item>()
                    .eq(Item::getId,id)
                    .set(Item::getName, UUID.randomUUID().toString())
            );
            stringRedisTemplate.delete(RedisConstant.DEFAULT_ITEM_PREFIX+id);
            return R.ok(itemMapper.selectById(id),"Update success");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
