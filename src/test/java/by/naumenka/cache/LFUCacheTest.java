package by.naumenka.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {

    LFUCache cache = new LFUCache();



    @Test
    void testLeastFrequentlyUsed(){
        for (int i = 0; i < 100200; i++) {
            cache.put(i, String.valueOf(i));
        }
        assertEquals("199 was deleted",cache.get(0));
    }
}