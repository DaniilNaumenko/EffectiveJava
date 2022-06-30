package by.naumenka.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;

import java.util.concurrent.TimeUnit;

public class GuavaCache {
    public Cache<Integer, String> guavaCache = CacheBuilder.newBuilder()
            .initialCapacity(100000)
            .removalListener((RemovalListener<Integer, String>) notification -> {
                System.out.println("Removed entry: " + notification.getKey() + " -> " + notification.getValue());
                System.out.println("Cause: " + notification.getCause().name());
            })
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

}

