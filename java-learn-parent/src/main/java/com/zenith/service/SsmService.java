package com.zenith.service;

import com.zenith.common.R;

public interface SsmService {
    R mybatisCacheLevelOne(Long id);

    R mybatisCacheLevelTwo(Long id);

    R transactionFailOne(Integer from , Integer to);
}
