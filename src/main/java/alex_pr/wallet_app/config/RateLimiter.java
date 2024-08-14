package alex_pr.wallet_app.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
/**
 * Configures a rate limiter using Bucket4j.
 * This class sets up a {@link Bucket} with a specific rate limit configuration.
 */
@Component
@RequiredArgsConstructor
public class RateLimiter {

    /**
     * Creates and configures a {@link Bucket} bean.
     *
     * @return A {@link Bucket} instance configured with a rate limit:
     *         - Capacity of 1000 tokens.
     *         - Refill interval of 1 second.
     */
    @Bean
    public Bucket bucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(1000)
                        .refillIntervally(1000, Duration.ofSeconds(1))
                        .build())
                .build();
    }
}

