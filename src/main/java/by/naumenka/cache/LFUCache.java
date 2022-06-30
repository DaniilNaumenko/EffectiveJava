package by.naumenka.cache;


import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class LFUCache {

    private static final Logger LOG = Logger.getLogger(LFUCache.class);

    private int capacity = 100000;
    private final Map<Integer, HitRate> cache = new HashMap<>();
    private final Map<Integer, String> keyValue = new HashMap<>();


    String removeLine;
    public int countRemoved = 0;


    public void put(int key, String value) {
        String v = keyValue.get(key);
        if (v == null) {
            if (cache.size() == capacity) {
                Integer k = getDeletedKey();
                removeLine = keyValue.get(k);
                LOG.info("LFU CACHE: DELETED " + removeLine);
                keyValue.remove(k);
                cache.remove(k);
                countRemoved++;
            }
            long startTimeNano = System.nanoTime();
            cache.put(key, new HitRate(key, 1, System.nanoTime()));
            long endTimeNano = System.nanoTime();
            String timeAdding = String.valueOf(endTimeNano - startTimeNano);
            LOG.info("LFU CACHE: TOOK " + timeAdding + " ms to add element");
        } else {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
        }
        keyValue.put(key, value);
    }

    public String get(int key) {
        String v = keyValue.get(key);
        if (v != null) {
            HitRate hitRate = cache.get(key);
            hitRate.hitCount += 1;
            hitRate.lastTime = System.nanoTime();
            return v;
        }
        return removeLine + " was deleted";
    }

    private Integer getDeletedKey() {
        HitRate min = Collections.min(cache.values());
        return min.key;
    }

    class HitRate implements Comparable<HitRate> {
        Integer key;
        Integer hitCount;
        Long lastTime;

        public HitRate(Integer key, Integer hitCount, Long lastTime) {
            this.key = key;
            this.hitCount = hitCount;
            this.lastTime = lastTime;
        }
        public int compareTo(HitRate o) {
            int hr = hitCount.compareTo(o.hitCount);
            return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
        }
    }
}
