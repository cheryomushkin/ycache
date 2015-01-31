package ru.datastructure.ycache;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheFactory;
import ru.datastructure.ycache.contract.CacheType;

public class CacheFactoryTestCase {

    private CacheFactory cacheFactory = CacheFactoryDefault.getInstance();

    public CacheFactory getCacheFactory() {
        return cacheFactory;
    }

    @Test
    public void testCreateFifo() {
        Cache<String, String> cacheFifo = getCacheFactory().createCache(CacheType.FIFO, 10);
        assertNotNull(cacheFifo);
        assertTrue(cacheFifo instanceof YcacheFifo);
    }

    @Test
    public void testCreateLru() {
        Cache<String, String> cacheFifo = getCacheFactory().createCache(CacheType.LRU, 10);
        assertNotNull(cacheFifo);
        assertTrue(cacheFifo instanceof YcacheLru);
    }

}
