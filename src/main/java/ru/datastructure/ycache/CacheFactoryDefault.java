package ru.datastructure.ycache;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Map;

import ru.datastructure.ycache.contract.Cache;
import ru.datastructure.ycache.contract.CacheFactory;
import ru.datastructure.ycache.contract.CacheType;

public class CacheFactoryDefault implements CacheFactory {

    private Map<CacheType, Class<?>> caches = new EnumMap<>(CacheType.class);

    private CacheFactoryDefault() {
        caches.put(CacheType.LRU, YcacheLru.class);
        caches.put(CacheType.FIFO, YcacheFifo.class);
    }

    public <K, V> Cache<K, V> createCache(CacheType fifo) {
        return createCache(fifo, 0);
    }

    @SuppressWarnings("unchecked")
    public <K, V> Cache<K, V> createCache(CacheType fifo, int capacity) {
        Cache<K, V> cache = null;
        if (caches.containsKey(fifo)) {
            Class<? extends Cache<K, V>> targetClass = (Class<? extends Cache<K, V>>) caches.get(fifo);
            try {
                if (capacity > 0) {
                    Constructor<? extends Cache<K, V>> constructor = targetClass.getConstructor(Integer.class);
                    cache = constructor.newInstance(capacity);
                } else {
                    cache = targetClass.newInstance();
                }

            } catch (RuntimeException | ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

    public static CacheFactoryDefault getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final CacheFactoryDefault INSTANCE = new CacheFactoryDefault();
    }
}
