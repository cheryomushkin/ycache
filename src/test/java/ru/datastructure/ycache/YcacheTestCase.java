package ru.datastructure.ycache;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheFactory;
import ru.datastructure.ycache.contract.CacheType;

public class YcacheTestCase {

    private CacheFactory cacheFactory = CacheFactoryDefault.getInstance();

    public CacheFactory getCacheFactory() {
        return cacheFactory;
    }

    @Test
    public void testYcacheFifoMainSuccess() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");

        cache.get(0);
        cache.get(1);

        cache.put(3, "value3");

        assertNull(cache.get(0));
    }

    @Test
    public void testYcacheFifoCapacity1() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 1);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");

        assertNull(cache.get(0));
        assertNotNull(cache.get(2));
    }

    @Test
    public void testYcacheFifoAddSameKey() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 1);
        testCacheGeneralSameKey(cache);
    }

    @Test
    public void testYcacheFifoSetGet() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 2);
        testCacheGeneralGetSet(cache);
    }

    @Test
    public void testYcacheFifoKeyDoesNotExist() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 2);
        testCacheGeneralKeyDoesNotExist(cache);
    }

    @Test
    public void testYcacheFifoKeyOverflow() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO, 2);
        testCacheGeneralOverflow(cache);
    }

    @Test
    public void testYcacheFifoKeySize() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.FIFO);
        testCacheGeneralSize(cache);
    }

    @Test
    public void testYcacheLruMainSuccessPut() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 3);
        // Fill the cache
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");

        // This call should invalidate "0"
        cache.put(3, "value3");
        assertNull("Element with id='0' must NOT be present in the cache", cache.get(0));

        // This call should invalidate "1"
        cache.put(4, "value4");
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }

    @Test
    public void testYcacheLrCapacity1() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 1);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");

        assertNull(cache.get(1));
        assertNotNull(cache.get(2));
    }

    @Test
    public void testYcacheLruAddSameKey() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 1);
        testCacheGeneralSameKey(cache);
    }

    @Test
    public void testYcacheLruSetGet() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 2);
        testCacheGeneralGetSet(cache);
    }

    @Test
    public void testYcacheLruKeyDoesNotExist() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 2);
        testCacheGeneralKeyDoesNotExist(cache);
    }

    @Test
    public void testYcacheLruKeyOverflow() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU, 2);
        testCacheGeneralOverflow(cache);
    }

    @Test
    public void testYcacheLruKeySize() {
        Cache<Integer, String> cache = getCacheFactory().createCache(CacheType.LRU);
        testCacheGeneralSize(cache);
    }

    private void testCacheGeneralGetSet(Cache<Integer, String> cache){
        cache.put(0, "value");
        cache.put(1, "value1");
        assertEquals("value", cache.get(0));
        assertEquals("value1", cache.get(1));
    }

    private void testCacheGeneralKeyDoesNotExist(Cache<Integer, String> cache){
        cache.put(0, "value");
        assertNull(cache.get(55));
    }

    private void testCacheGeneralOverflow(Cache<Integer, String> cache){
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        assertNull(cache.get(0));
    }

    private void testCacheGeneralSameKey(Cache<Integer, String> cache){
        cache.put(0, "value");
        cache.put(0, "value1");
        cache.put(0, "value2");

        assertEquals("value2", cache.get(0));
    }

    private void testCacheGeneralSize(Cache<Integer, String> cache){
        for (int i = 0; i < 100; i++) {
            cache.put(0, "value");
        }

        assertEquals(1, cache.size());
    }

}
