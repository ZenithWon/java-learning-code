package com.zenith.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zenith.entity.Item;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@CacheNamespace
public interface ItemMapper extends BaseMapper<Item> {
}
