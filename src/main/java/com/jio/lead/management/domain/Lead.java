package com.jio.lead.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Lead.
 */
@Entity
@Table(name = "lead")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lead")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lead" }, allowSetters = true)
    private Set<Interest> interests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lead id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Lead name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Lead phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Lead createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Lead createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Lead updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Lead updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Interest> getInterests() {
        return this.interests;
    }

    public void setInterests(Set<Interest> interests) {
        if (this.interests != null) {
            this.interests.forEach(i -> i.setLead(null));
        }
        if (interests != null) {
            interests.forEach(i -> i.setLead(this));
        }
        this.interests = interests;
    }

    public Lead interests(Set<Interest> interests) {
        this.setInterests(interests);
        return this;
    }

    public Lead addInterest(Interest interest) {
        this.interests.add(interest);
        interest.setLead(this);
        return this;
    }

    public Lead removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.setLead(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lead)) {
            return false;
        }
        return getId() != null && getId().equals(((Lead) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lead{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone=" + getPhone() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
