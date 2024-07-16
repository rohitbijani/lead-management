package com.jio.lead.management.web.rest;

import static com.jio.lead.management.domain.InterestAsserts.*;
import static com.jio.lead.management.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jio.lead.management.IntegrationTest;
import com.jio.lead.management.domain.Interest;
import com.jio.lead.management.repository.InterestRepository;
import com.jio.lead.management.service.dto.InterestDTO;
import com.jio.lead.management.service.mapper.InterestMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InterestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterestResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PURCHASED = false;
    private static final Boolean UPDATED_IS_PURCHASED = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/interests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private InterestMapper interestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestMockMvc;

    private Interest interest;

    private Interest insertedInterest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createEntity(EntityManager em) {
        Interest interest = new Interest()
            .category(DEFAULT_CATEGORY)
            .brand(DEFAULT_BRAND)
            .articleId(DEFAULT_ARTICLE_ID)
            .orderId(DEFAULT_ORDER_ID)
            .isPurchased(DEFAULT_IS_PURCHASED)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY);
        return interest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interest createUpdatedEntity(EntityManager em) {
        Interest interest = new Interest()
            .category(UPDATED_CATEGORY)
            .brand(UPDATED_BRAND)
            .articleId(UPDATED_ARTICLE_ID)
            .orderId(UPDATED_ORDER_ID)
            .isPurchased(UPDATED_IS_PURCHASED)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        return interest;
    }

    @BeforeEach
    public void initTest() {
        interest = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedInterest != null) {
            interestRepository.delete(insertedInterest);
            insertedInterest = null;
        }
    }

    @Test
    @Transactional
    void createInterest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);
        var returnedInterestDTO = om.readValue(
            restInterestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InterestDTO.class
        );

        // Validate the Interest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInterest = interestMapper.toEntity(returnedInterestDTO);
        assertInterestUpdatableFieldsEquals(returnedInterest, getPersistedInterest(returnedInterest));

        insertedInterest = returnedInterest;
    }

    @Test
    @Transactional
    void createInterestWithExistingId() throws Exception {
        // Create the Interest with an existing ID
        interest.setId(1L);
        InterestDTO interestDTO = interestMapper.toDto(interest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        interest.setCategory(null);

        // Create the Interest, which fails.
        InterestDTO interestDTO = interestMapper.toDto(interest);

        restInterestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInterests() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get all the interestList
        restInterestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interest.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].articleId").value(hasItem(DEFAULT_ARTICLE_ID)))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].isPurchased").value(hasItem(DEFAULT_IS_PURCHASED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        // Get the interest
        restInterestMockMvc
            .perform(get(ENTITY_API_URL_ID, interest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interest.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.articleId").value(DEFAULT_ARTICLE_ID))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.isPurchased").value(DEFAULT_IS_PURCHASED.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingInterest() throws Exception {
        // Get the interest
        restInterestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest
        Interest updatedInterest = interestRepository.findById(interest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInterest are not directly saved in db
        em.detach(updatedInterest);
        updatedInterest
            .category(UPDATED_CATEGORY)
            .brand(UPDATED_BRAND)
            .articleId(UPDATED_ARTICLE_ID)
            .orderId(UPDATED_ORDER_ID)
            .isPurchased(UPDATED_IS_PURCHASED)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);
        InterestDTO interestDTO = interestMapper.toDto(updatedInterest);

        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInterestToMatchAllProperties(updatedInterest);
    }

    @Test
    @Transactional
    void putNonExistingInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        partialUpdatedInterest
            .category(UPDATED_CATEGORY)
            .articleId(UPDATED_ARTICLE_ID)
            .orderId(UPDATED_ORDER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterestUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedInterest, interest), getPersistedInterest(interest));
    }

    @Test
    @Transactional
    void fullUpdateInterestWithPatch() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the interest using partial update
        Interest partialUpdatedInterest = new Interest();
        partialUpdatedInterest.setId(interest.getId());

        partialUpdatedInterest
            .category(UPDATED_CATEGORY)
            .brand(UPDATED_BRAND)
            .articleId(UPDATED_ARTICLE_ID)
            .orderId(UPDATED_ORDER_ID)
            .isPurchased(UPDATED_IS_PURCHASED)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedAt(UPDATED_UPDATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInterest))
            )
            .andExpect(status().isOk());

        // Validate the Interest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInterestUpdatableFieldsEquals(partialUpdatedInterest, getPersistedInterest(partialUpdatedInterest));
    }

    @Test
    @Transactional
    void patchNonExistingInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(interestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        interest.setId(longCount.incrementAndGet());

        // Create the Interest
        InterestDTO interestDTO = interestMapper.toDto(interest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(interestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Interest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterest() throws Exception {
        // Initialize the database
        insertedInterest = interestRepository.saveAndFlush(interest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the interest
        restInterestMockMvc
            .perform(delete(ENTITY_API_URL_ID, interest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return interestRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Interest getPersistedInterest(Interest interest) {
        return interestRepository.findById(interest.getId()).orElseThrow();
    }

    protected void assertPersistedInterestToMatchAllProperties(Interest expectedInterest) {
        assertInterestAllPropertiesEquals(expectedInterest, getPersistedInterest(expectedInterest));
    }

    protected void assertPersistedInterestToMatchUpdatableProperties(Interest expectedInterest) {
        assertInterestAllUpdatablePropertiesEquals(expectedInterest, getPersistedInterest(expectedInterest));
    }
}
