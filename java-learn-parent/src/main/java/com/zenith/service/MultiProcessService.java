package com.zenith.service;

import com.zenith.common.R;

public interface MultiProcessService {
    R startByThread();

    R startByRunnable();

    R startByCallable();

    R startByPool();
}
