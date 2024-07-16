package com.jio.lead.management.service.impl;

import com.jio.lead.management.domain.Lead;
import com.jio.lead.management.repository.LeadRepository;
import com.jio.lead.management.service.LeadService;
import com.jio.lead.management.service.dto.LeadDTO;
import com.jio.lead.management.service.mapper.LeadMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.jio.lead.management.domain.Lead}.
 */
@Service
@Transactional
public class LeadServiceImpl implements LeadService {

    private static final Logger log = LoggerFactory.getLogger(LeadServiceImpl.class);

    private final LeadRepository leadRepository;

    private final LeadMapper leadMapper;

    public LeadServiceImpl(LeadRepository leadRepository, LeadMapper leadMapper) {
        this.leadRepository = leadRepository;
        this.leadMapper = leadMapper;
    }

    @Override
    public LeadDTO save(LeadDTO leadDTO) {
        log.debug("Request to save Lead : {}", leadDTO);
        Lead lead = leadMapper.toEntity(leadDTO);
        lead = leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public LeadDTO update(LeadDTO leadDTO) {
        log.debug("Request to update Lead : {}", leadDTO);
        Lead lead = leadMapper.toEntity(leadDTO);
        lead = leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public Optional<LeadDTO> partialUpdate(LeadDTO leadDTO) {
        log.debug("Request to partially update Lead : {}", leadDTO);

        return leadRepository
            .findById(leadDTO.getId())
            .map(existingLead -> {
                leadMapper.partialUpdate(existingLead, leadDTO);

                return existingLead;
            })
            .map(leadRepository::save)
            .map(leadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leads");
        return leadRepository.findAll(pageable).map(leadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeadDTO> findOne(Long id) {
        log.debug("Request to get Lead : {}", id);
        return leadRepository.findById(id).map(leadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lead : {}", id);
        leadRepository.deleteById(id);
    }
}
