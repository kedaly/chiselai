package ca.chisel.interview;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An LRU Implementation of A Cache
 *
 * @param <K> key of the cache value
 * @param <V> the value of the
 * @see <a href="https://en.wikipedia.org/wiki/Cache_replacement_policies#Least_recently_used_(LRU)"</a> <p> Data structures stored in this cache should implement equals and hashCode
 */
public class LRUCache<K, V> implements Cache<K, V> {
    private final int maxSize;
    private final IndexedDoubleLinkedList<K,V> backingList;
    private final ReentrantLock lock = new ReentrantLock();
    private final AtomicLong reads = new AtomicLong();
    private final AtomicLong hits = new AtomicLong();
    private final AtomicLong writes = new AtomicLong();
    private final AtomicLong evictions = new AtomicLong();

    /**
     * Instantiates a new Lru cache.
     *
     * @param cacheSize the size of the cache
     */
    public LRUCache(final int cacheSize) {
        this.maxSize = cacheSize;
        backingList = new IndexedDoubleLinkedList<>(cacheSize);
    }


    @Override
    public void put(final K key, final V value) {
        lock.lock();
        try {
            //remove tail if we're over capacity
            if (backingList.size() == maxSize) {
                backingList.remove(backingList.getTail().getKey());
                evictions.incrementAndGet();
            }

            backingList.putHead(key,value);
        } finally {
            lock.unlock();
        }
    }


    @Override
    public V get(final K key) {
        lock.lock();
        try {
            reads.incrementAndGet();
             final V candidate = backingList.get(key);

            if (candidate != null) {
                hits.incrementAndGet();

                //move the hit to top
                backingList.putHead(key,candidate);

                return candidate;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void del(final K key) {
        lock.lock();
        try {
            backingList.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void reset() {
        backingList.clear();
    }

    @Override
    public boolean cacheContains(final K key) {
        return backingList.containsKey(key);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> entrySet = new HashSet<>(backingList.size());

        for (final Map.Entry<K,V> entry : backingList) {
            entrySet.add(entry);
        }
        return entrySet;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public int getCurrentSize() {
        return backingList.size();
    }

    /**
     * Gets hits.
     *
     * @return the hits
     */
    public long getHits() {
        return hits.get();
    }

    /**
     * Gets evictions.
     *
     * @return the evictions
     */
    public long getEvictions() {
        return  evictions.get();
    }

    /**
     * Gets reads.
     *
     * @return the reads
     */
    public long getReads() {
        return reads.get();
    }

    /**
     * Gets writes.
     *
     * @return the writes
     */
    public long getWrites() {
        return writes.get();
    }
}

