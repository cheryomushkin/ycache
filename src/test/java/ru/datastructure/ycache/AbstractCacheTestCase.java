package ru.datastructure.ycache;

import static org.junit.Assert.*;

import org.junit.Test;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheFactory;
import ru.datastructure.ycache.contract.CacheType;

public abstract class AbstractCacheTestCase {

    private CacheFactory cacheFactory = CacheFactoryDefault.getInstance();
    private CacheType cacheType;

    AbstractCacheTestCase(CacheType cacheType) {
        this.cacheType = cacheType;

    }

    /**
     * Creates cache with max capacity = 100
     */
    protected <T, K> Cache<T, K> createCache(){
        return cacheFactory.createCache(cacheType);
    }

    /**
     * Creates cache with provided capacity.
     * If capacity less than zero it will throw IllegalArgumentException
     */
    protected <T, K> Cache<T, K> createCache(int capacity){
        return cacheFactory.createCache(cacheType, capacity);
    }

    @Test
    public void testCacheGeneralCapacityOfZero() {
        Cache<Integer, String> cache = createCache(0);

        cache.put(0, "value");
        assertEquals("Element must have not been stored", 0, cache.size());

        assertNull("There must not be element with id='0'", cache.get(0));
    }

    @Test
    public void testCacheGeneralCapacityOfOne() {
        Cache<Integer, String> cache = createCache(1);

        cache.put(0, "value");
        assertEquals("One element is added, size must be eqauls 1", 1, cache.size());

        cache.put(1, "value1");
        assertEquals("Another element is added, but size must be eqauls 1", 1, cache.size());
        assertNull("Element with id='0' must NOT be available", cache.get(0));
        assertNotNull("Element with id='1' must be available", cache.get(1));
    }

    @Test
    public void testCacheGeneralKeyDoesNotExist() {
        Cache<Integer, String> cache = createCache();

        cache.put(0, "value");
        assertNull(cache.get(55));
    }

    /**
     * This test uses only put operations so it works for FIFO and LRU types
     * correctly
     */
    @Test
    public void testCacheGeneralOverflow() {
        Cache<Integer, String> cache = createCache(3);

        cache.put(0, "value");
        cache.put(1, "value1");
        cache.put(2, "value2");
        cache.put(3, "value3");

        assertNull(cache.get(0));
    }

    @Test
    public void testCacheGeneralSameKey() {
        Cache<Integer, String> cache = createCache(1);

        cache.put(0, "value");
        cache.put(0, "value1");
        cache.put(0, "value2");

        assertEquals("value2", cache.get(0));
    }

    @Test
    public void testCacheGeneralSize() {
        Cache<Integer, String> cache = createCache();

        for (int i = 0; i < 100; i++) {
            cache.put(0, "value");
        }

        assertEquals(1, cache.size());
    }

    @Test
    public void testCacheGeneralPutGetSameObject() {
        Cache<Integer, Object> cache = createCache();

        Object zero = new Object();
        Object first = new Object();
        Object second = new Object();

        cache.put(0, zero);
        cache.put(1, first);
        cache.put(2, second);

        assertSame(zero, cache.get(0));
        assertSame(first, cache.get(1));
        assertSame(second, cache.get(2));
    }

    @Test
    public void testCacheGeneralCapacity() {
        Cache<Integer, Object> cache = createCache(55);

        assertEquals(55, cache.getMaxCapacity());
    }

}
