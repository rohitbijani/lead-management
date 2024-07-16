package com.jio.lead.management.service.mapper;

import static com.jio.lead.management.domain.InterestAsserts.*;
import static com.jio.lead.management.domain.InterestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterestMapperTest {

    private InterestMapper interestMapper;

    @BeforeEach
    void setUp() {
        interestMapper = new InterestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getInterestSample1();
        var actual = interestMapper.toEntity(interestMapper.toDto(expected));
        assertInterestAllPropertiesEquals(expected, actual);
    }
}
