package ru.datastructure.ycache.contract;

public interface CacheFactory {
    <T, K> Cache<T, K> createCache(CacheType fifo);

    <T, K> Cache<T, K> createCache(CacheType fifo, int capacity);
}
