package com.jio.lead.management.service.mapper;

import com.jio.lead.management.domain.Lead;
import com.jio.lead.management.service.dto.LeadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lead} and its DTO {@link LeadDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeadMapper extends EntityMapper<LeadDTO, Lead> {}
