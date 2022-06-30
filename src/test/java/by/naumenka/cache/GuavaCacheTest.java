package by.naumenka.cache;

import com.google.common.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuavaCacheTest {

    Cache<Integer, String> cache = new GuavaCache().guavaCache;

    @BeforeEach
    void clear() {
        cache.cleanUp();
    }

    @Test
    void testLeastRecentlyUsed () throws InterruptedException {
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four");
        cache.put(5, "Five");
        cache.put(6, "Six");
        cache.put(7, "Seven");
        Thread.sleep(6000);
        Assertions.assertNull(cache.asMap().get(5));

    }
}