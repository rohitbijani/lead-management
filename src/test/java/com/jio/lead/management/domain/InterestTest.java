package com.jio.lead.management.domain;

import static com.jio.lead.management.domain.InterestTestSamples.*;
import static com.jio.lead.management.domain.LeadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jio.lead.management.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interest.class);
        Interest interest1 = getInterestSample1();
        Interest interest2 = new Interest();
        assertThat(interest1).isNotEqualTo(interest2);

        interest2.setId(interest1.getId());
        assertThat(interest1).isEqualTo(interest2);

        interest2 = getInterestSample2();
        assertThat(interest1).isNotEqualTo(interest2);
    }

    @Test
    void leadTest() {
        Interest interest = getInterestRandomSampleGenerator();
        Lead leadBack = getLeadRandomSampleGenerator();

        interest.setLead(leadBack);
        assertThat(interest.getLead()).isEqualTo(leadBack);

        interest.lead(null);
        assertThat(interest.getLead()).isNull();
    }
}
