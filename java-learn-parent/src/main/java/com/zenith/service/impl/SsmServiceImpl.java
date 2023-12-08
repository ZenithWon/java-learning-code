package com.zenith.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zenith.common.MyException;
import com.zenith.common.R;
import com.zenith.entity.Item;
import com.zenith.mapper.ItemMapper;
import com.zenith.service.SsmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SsmServiceImpl implements SsmService {
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    ItemMapper itemMapper;


    @Override
    public R mybatisCacheLevelOne(Long id) {
        Map<String,Item> res=new HashMap<>();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemMapper mapper = sqlSession.getMapper(ItemMapper.class);
        Item item = mapper.selectById(id);
        res.put("First select",item);

        item = mapper.selectById(id);
        res.put("Second select",item);

        return R.ok(res);
    }

    @Override
    public R mybatisCacheLevelTwo(Long id) {
        Map<String,Item> res=new HashMap<>();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemMapper mapper = sqlSession.getMapper(ItemMapper.class);
        Item item = mapper.selectById(id);
        res.put("First select",item);
        sqlSession.close();

        sqlSession = sqlSessionFactory.openSession();
        mapper=sqlSession.getMapper(ItemMapper.class);
        item = mapper.selectById(id);
        res.put("Second select",item);

        return R.ok(res);
    }

    @Override
    @Transactional
    public R transactionFailOne(Integer from , Integer to) {
        Map<String,Item> map= new HashMap<>();
        try {
            Item fromItem = itemMapper.selectById(from);
            Item toItem = itemMapper.selectById(to);
            map.put("Before fromItem",fromItem);
            map.put("Before toItem",toItem);

            itemMapper.update(null,
                    new LambdaUpdateWrapper<Item>()
                            .eq(Item::getId,from)
                            .set(Item::getCount,fromItem.getCount()-1)
            );
            int i=1/0;//Test for transaction

            itemMapper.update(null,
                    new LambdaUpdateWrapper<Item>()
                            .eq(Item::getId,to)
                            .set(Item::getCount,toItem.getCount()+1)
            );
        }catch (Exception e){
            e.printStackTrace();
            Item fromItem = itemMapper.selectById(from);
            Item toItem = itemMapper.selectById(to);
            map.put("After fromItem",fromItem);
            map.put("After toItem",toItem);
//            throw new MyException("Test for transaction");
        }
        return R.ok(map);
    }
}
