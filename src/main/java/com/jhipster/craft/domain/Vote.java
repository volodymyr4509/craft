package com.jhipster.craft.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Vote.
 */
@Entity
@Table(name = "vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matched")
    private Boolean matched;

    @Column(name = "variants")
    private Integer variants;

    @Column(name = "create_ts")
    private LocalDate createTS;

    @Column(name = "update_ts")
    private LocalDate updateTS;

    @OneToOne
    @JoinColumn(unique = true)
    private User elector;

    @OneToOne
    @JoinColumn(unique = true)
    private User candidate;

    @ManyToOne
    private Dog dog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMatched() {
        return matched;
    }

    public Vote matched(Boolean matched) {
        this.matched = matched;
        return this;
    }

    public void setMatched(Boolean matched) {
        this.matched = matched;
    }

    public Integer getVariants() {
        return variants;
    }

    public Vote variants(Integer variants) {
        this.variants = variants;
        return this;
    }

    public void setVariants(Integer variants) {
        this.variants = variants;
    }

    public LocalDate getCreateTS() {
        return createTS;
    }

    public Vote createTS(LocalDate createTS) {
        this.createTS = createTS;
        return this;
    }

    public void setCreateTS(LocalDate createTS) {
        this.createTS = createTS;
    }

    public LocalDate getUpdateTS() {
        return updateTS;
    }

    public Vote updateTS(LocalDate updateTS) {
        this.updateTS = updateTS;
        return this;
    }

    public void setUpdateTS(LocalDate updateTS) {
        this.updateTS = updateTS;
    }

    public User getElector() {
        return elector;
    }

    public Vote elector(User user) {
        this.elector = user;
        return this;
    }

    public void setElector(User user) {
        this.elector = user;
    }

    public User getCandidate() {
        return candidate;
    }

    public Vote candidate(User user) {
        this.candidate = user;
        return this;
    }

    public void setCandidate(User user) {
        this.candidate = user;
    }

    public Dog getDog() {
        return dog;
    }

    public Vote dog(Dog dog) {
        this.dog = dog;
        return this;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vote vote = (Vote) o;
        if (vote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Vote{" +
            "id=" + getId() +
            ", matched='" + isMatched() + "'" +
            ", variants=" + getVariants() +
            ", createTS='" + getCreateTS() + "'" +
            ", updateTS='" + getUpdateTS() + "'" +
            "}";
    }
}
