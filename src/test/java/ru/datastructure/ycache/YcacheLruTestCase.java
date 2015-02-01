package ru.datastructure.ycache;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheType;

public class YcacheLruTestCase extends AbstractCacheTestCase {

    public YcacheLruTestCase() {
        super(CacheType.LRU);
    }

    @Test
    public void testYcacheLruMainSuccessPut() {
        Cache<Integer, String> cache = createCache(3);
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
    public void testYcacheLruMainSuccessGet() {
        Cache<Integer, String> cache = createCache(3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");

        cache.get(0);
        cache.get(1);

        // This call should invalidate element with id="2"
        cache.put(4, "value4");
        assertNull("Element with id='2' must NOT be present in the cache", cache.get(2));

        cache.get(0);
        // This call should invalidate element with id="1"
        cache.put(5, "value5");
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }
}
