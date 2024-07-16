package com.jio.lead.management.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.jio.lead.management.domain.Interest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestDTO implements Serializable {

    private Long id;

    @NotNull
    private String category;

    private String brand;

    private String articleId;

    private String orderId;

    private Boolean isPurchased;

    private Instant createdAt;

    private String createdBy;

    private Instant updatedAt;

    private String updatedBy;

    private LeadDTO lead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LeadDTO getLead() {
        return lead;
    }

    public void setLead(LeadDTO lead) {
        this.lead = lead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterestDTO)) {
            return false;
        }

        InterestDTO interestDTO = (InterestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, interestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", brand='" + getBrand() + "'" +
            ", articleId='" + getArticleId() + "'" +
            ", orderId='" + getOrderId() + "'" +
            ", isPurchased='" + getIsPurchased() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", lead=" + getLead() +
            "}";
    }
}
