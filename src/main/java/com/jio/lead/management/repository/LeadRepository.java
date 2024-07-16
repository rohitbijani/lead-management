package com.jio.lead.management.repository;

import com.jio.lead.management.domain.Lead;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Lead entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {}
