package ru.datastructure.ycache;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheType;

public class YcacheFifoTestCase extends AbstractCacheTestCase {

    public YcacheFifoTestCase() {
        super(CacheType.FIFO);
    }

    @Test
    public void testYcacheFifoMainSuccess() {
        Cache<Integer, String> cache = createCache(3);
        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        //{0, 1, 2}

        // accessing element
        // this operation must not have any effect on cache invalidation strategy
        cache.get(1);
        cache.get(1);
        // This element must displace element with id="0"
        cache.put(3, "value3"); // {[0], 1, 2} --> {1, 2, [3]}

        assertNotNull(cache.get(1));
        assertNotNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNull(cache.get(0));

        // accessing element
        // this operation must not have any effect on cache invalidation strategy
        cache.get(1);
        cache.get(1);
        // This element must displace element with id="1"
        cache.put(4, "value4"); // {[1], 2, 3} --> {2, 3, [4]}

        assertNotNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNull(cache.get(1));

        // accessing element
        // this operation must not have any effect on cache invalidation strategy
        cache.get(2);
        cache.get(2);
        // This element must displace element with id="2"
        cache.put(5, "value5"); // {[2], 3, 4} --> {3, 4, [5]}

        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNotNull(cache.get(5));
        assertNull(cache.get(2));
    }
}
