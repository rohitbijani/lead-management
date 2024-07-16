package com.jio.lead.management.service.mapper;

import com.jio.lead.management.domain.Interest;
import com.jio.lead.management.domain.Lead;
import com.jio.lead.management.service.dto.InterestDTO;
import com.jio.lead.management.service.dto.LeadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Interest} and its DTO {@link InterestDTO}.
 */
@Mapper(componentModel = "spring")
public interface InterestMapper extends EntityMapper<InterestDTO, Interest> {
    @Mapping(target = "lead", source = "lead", qualifiedByName = "leadId")
    InterestDTO toDto(Interest s);

    @Named("leadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadDTO toDtoLeadId(Lead lead);
}
