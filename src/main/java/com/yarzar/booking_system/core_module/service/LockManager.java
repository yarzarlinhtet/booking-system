package com.yarzar.booking_system.core_module.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LockManager {

    private final ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<>();

    public ReentrantLock getLock(String lockKey) {
        return locks.computeIfAbsent(lockKey, id -> new ReentrantLock());
    }

    public void releaseLock(String lockKey) {
        ReentrantLock lock = locks.get(lockKey);
        if (lock != null) {
            lock.unlock();
            locks.remove(lockKey);
        }
    }
}
