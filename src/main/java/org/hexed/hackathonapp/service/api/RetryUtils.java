package org.hexed.hackathonapp.service.api;

import java.util.function.Supplier;

public class RetryUtils {

    public static <T> T retry(Supplier<T> supplier, int maxRetries) {
        int attempts = 0;
        while (true) {
            try {
                T result = supplier.get();
                if (result instanceof Integer && (Integer) result < 0) {
                    throw new RuntimeException("Retry triggered: result < 0");
                }

                return result;
            } catch (Exception e) {
                attempts++;
                if (attempts > maxRetries) {
                    throw e;
                }
            }
        }
    }
}