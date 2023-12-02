package com.zenith.service;

import com.zenith.common.R;

public interface RedisService {
    R cacheThroughNullSolution(Long id);

    R cacheThroughBloomSolution(Long id);

    R hitThroughMutexSolution(Long id);

    R hitThroughMLogicalTtlSolution(Long id);

    R doubleWriteLockRead(Long id);

    R doubleWriteLockWrite(Long id);
}
