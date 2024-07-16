package com.jio.lead.management.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InterestTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Interest getInterestSample1() {
        return new Interest()
            .id(1L)
            .category("category1")
            .brand("brand1")
            .articleId("articleId1")
            .orderId("orderId1")
            .createdBy("createdBy1")
            .updatedBy("updatedBy1");
    }

    public static Interest getInterestSample2() {
        return new Interest()
            .id(2L)
            .category("category2")
            .brand("brand2")
            .articleId("articleId2")
            .orderId("orderId2")
            .createdBy("createdBy2")
            .updatedBy("updatedBy2");
    }

    public static Interest getInterestRandomSampleGenerator() {
        return new Interest()
            .id(longCount.incrementAndGet())
            .category(UUID.randomUUID().toString())
            .brand(UUID.randomUUID().toString())
            .articleId(UUID.randomUUID().toString())
            .orderId(UUID.randomUUID().toString())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
