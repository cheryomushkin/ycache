package ru.datastructure.ycache.contract;

public interface CacheFactory {
    /**
     * Creates cache with max capacity = 100
     */
    <T, K> Cache<T, K> createCache(CacheType fifo);

    /**
     * Creates cache with desirable capacity.
     * If the capacity is less than zero it will throw IllegalArgumentException
     */
    <T, K> Cache<T, K> createCache(CacheType fifo, int capacity);
}
