package com.da.lect3.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantDowngrade {
    private Object data;
    private volatile boolean cacheValid;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    void processCacheData() {
        // first acquire a read lock
        rwl.readLock().lock();
        // check if cache is still valid
        if (!cacheValid) {
            // Must release read lock before acquiring
            // write lock, as upgrading not possible
            rwl.readLock().unlock();
            rwl.writeLock().lock();
            try {
                // Recheck state because another thread might have
                // acquired write lock and changed state before we did.
                if (!cacheValid) {
                    // get fresh data for the cache
                    //data = ...
                    cacheValid = true;
                }
                // Downgrade by acquiring read lock before
                // releasing write lock
                rwl.readLock().lock();
            } finally {
                // Unlock write, still hold read
                rwl.writeLock().unlock();
            }
        }
        try {
            // use cache data
            //use(data);
        } finally {
            // Finally release the read lock
            rwl.readLock().unlock();
        }
    }
}
