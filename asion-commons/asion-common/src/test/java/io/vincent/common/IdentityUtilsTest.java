package io.vincent.common;

import io.vincent.common.utils.IdentityUtils;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Asion.
 * @since 16/7/28.
 */
public class IdentityUtilsTest {

    private ConcurrentMap<Long, Long> concurrentMap = new ConcurrentHashMap<>();

    @Test
    public void  testGenerateUniqueIdentity() {

        Long uniqueIdentity = IdentityUtils.INSTANCE.generateLongUUID();
        System.out.println(uniqueIdentity);

        ExecutorService exec = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 1000; i++) {
            exec.execute(() -> {
                String threadId = Thread.currentThread().getId() + Thread.currentThread().getName();
                Long id = IdentityUtils.INSTANCE.get13DigitsUId();
                System.out.println(threadId + ", [" + id + "]");
                Long cacheId = concurrentMap.get(id);

                concurrentMap.put(id, id);
                if (Objects.equals(id, cacheId)) {
                    System.out.println(threadId + "Duplicated: "+id);
                }
            });
        }
        System.out.println("digits size: " + concurrentMap.size());
    }

}
