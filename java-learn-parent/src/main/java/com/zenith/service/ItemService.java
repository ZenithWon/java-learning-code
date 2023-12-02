package com.zenith.service;

import com.zenith.common.R;
import com.zenith.entity.Item;

import java.util.UUID;

public interface ItemService {
    R generateItemData(Integer num);

    R generateLogicalTTLItemData();
}
