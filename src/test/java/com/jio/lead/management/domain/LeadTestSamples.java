package com.jio.lead.management.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeadTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Lead getLeadSample1() {
        return new Lead().id(1L).name("name1").phone(1L).createdBy("createdBy1").updatedBy("updatedBy1");
    }

    public static Lead getLeadSample2() {
        return new Lead().id(2L).name("name2").phone(2L).createdBy("createdBy2").updatedBy("updatedBy2");
    }

    public static Lead getLeadRandomSampleGenerator() {
        return new Lead()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .createdBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
