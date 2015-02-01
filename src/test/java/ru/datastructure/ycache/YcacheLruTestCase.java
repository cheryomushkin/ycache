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
        // {2 -> 1 -> 0}

        // This call should remove "0"
        cache.put(3, "value3"); // {3 -> 2 -> 1} 0 -> delete
        assertNull("Element with id='0' must NOT be present in the cache", cache.get(0));

        // This call should remove "1"
        cache.put(4, "value4"); // {4 -> 3 -> 2} 1 -> delete
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }

    @Test
    public void testYcacheLruMainSuccessGetFirst() {
        Cache<Integer, String> cache = createCache(3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        // {2 -> 1 -> 0}

        cache.get(2); // {2 -> 1 -> 0}
        // This call should remove element with id="0"
        cache.put(3, "value3"); // {3 -> 2 -> 1} 0 -> delete
        assertNull("Element with id='0' must NOT be present in the cache", cache.get(0));

        cache.get(3); // {3 -> 2 -> 1}
        // This call should remove element with id="1"
        cache.put(4, "value4"); // {4 -> 3 -> 2} 1 -> delete
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }

    @Test
    public void testYcacheLruMainSuccessGetLast() {
        Cache<Integer, String> cache = createCache(3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        // {2 -> 1 -> 0}

        cache.get(0); // {0 -> 2 -> 1}
        // This call should remove element with id="1"
        cache.put(3, "value3"); // {3 -> 0 -> 2} 1 -> delete
        assertNull("Element with id='0' must NOT be present in the cache", cache.get(1));

        cache.get(2);// {2 -> 3 -> 0}
        // This call should remove element with id="0"
        cache.put(4, "value4"); // {4 -> 2 -> 3} 0 -> delete
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }

    @Test
    public void testYcacheLruMainSuccessGetFromTheMiddle() {
        Cache<Integer, String> cache = createCache(3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        // {2 -> 1 -> 0}

        cache.get(1); // {1 -> 2 -> 0}

        // This call should remove element with id="0"
        cache.put(3, "value3"); // {3 -> 1 -> 2 } 0 -> delete
        assertNull("Element with id='2' must NOT be present in the cache", cache.get(0));

        cache.get(2); // {2 -> 3 -> 1 }
        // This call should remove element with id="1"
        cache.put(4, "value4"); // {4 -> 2 -> 3} 1 -> delete
        assertNull("Element with id='1' must NOT be present in the cache", cache.get(1));
    }
}
