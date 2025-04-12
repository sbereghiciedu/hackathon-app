package org.hexed.hackathonapp.service.api;

import org.hexed.hackathonapp.controller.DebugController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.function.Supplier;

public class RetryUtils {

    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    public static <T> T retry(Supplier<T> supplier, int maxRetries) {
        int attempts = 0;
        while (true) {
            try {
                T result = supplier.get();
                if (result instanceof Integer && (Integer) result < 0) {
                    logger.info("Invalid result: negative. Retrying");
                    throw new RuntimeException("Retry triggered: result < 0");
                }

                return result;
            } catch (WebClientResponseException.Unauthorized e) {
                logger.info("Unauthorized, refresh token");
            } catch (Exception e) {
                logger.info("Server error, retrying");
                attempts++;
                if (attempts > maxRetries) {
                    throw e;
                }
            }
        }
    }
}