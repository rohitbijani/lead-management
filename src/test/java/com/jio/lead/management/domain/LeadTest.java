package com.jio.lead.management.domain;

import static com.jio.lead.management.domain.InterestTestSamples.*;
import static com.jio.lead.management.domain.LeadTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.jio.lead.management.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LeadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lead.class);
        Lead lead1 = getLeadSample1();
        Lead lead2 = new Lead();
        assertThat(lead1).isNotEqualTo(lead2);

        lead2.setId(lead1.getId());
        assertThat(lead1).isEqualTo(lead2);

        lead2 = getLeadSample2();
        assertThat(lead1).isNotEqualTo(lead2);
    }

    @Test
    void interestTest() {
        Lead lead = getLeadRandomSampleGenerator();
        Interest interestBack = getInterestRandomSampleGenerator();

        lead.addInterest(interestBack);
        assertThat(lead.getInterests()).containsOnly(interestBack);
        assertThat(interestBack.getLead()).isEqualTo(lead);

        lead.removeInterest(interestBack);
        assertThat(lead.getInterests()).doesNotContain(interestBack);
        assertThat(interestBack.getLead()).isNull();

        lead.interests(new HashSet<>(Set.of(interestBack)));
        assertThat(lead.getInterests()).containsOnly(interestBack);
        assertThat(interestBack.getLead()).isEqualTo(lead);

        lead.setInterests(new HashSet<>());
        assertThat(lead.getInterests()).doesNotContain(interestBack);
        assertThat(interestBack.getLead()).isNull();
    }
}
