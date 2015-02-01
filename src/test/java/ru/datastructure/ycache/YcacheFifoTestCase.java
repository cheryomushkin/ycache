package ru.datastructure.ycache;

import static org.junit.Assert.assertNull;

import java.util.Random;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheType;

public class YcacheFifoTestCase extends AbstractCacheTestCase {

    public YcacheFifoTestCase() {
        super(CacheType.FIFO);
    }

    @Test
    public void testYcacheFifoMainSuccess() {
        Cache<Integer, String> cache = createCache(100);

        for (int i = 0; i < 100; i++) {
            cache.put(i, "value" + i);
        }
        // accessing elements randomly
        // these operations must have any effect on cache invalidation strategy
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            cache.get(random.nextInt(100));
        }
        // This element must displace element with id="0"
        cache.put(100, "value" + 100);

        assertNull(cache.get(0));
    }
}
