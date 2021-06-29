package com.mindeavors.queuen.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BranchService.
 */
@Entity
@Table(name = "branch_service")
public class BranchService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "starting_time")
    private Instant startingTime;

    @Column(name = "ending_time")
    private Instant endingTime;

    @NotNull
    @Column(name = "average_serving_minutes", nullable = false)
    private Double averageServingMinutes;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "last_deactivation_by_super_admin")
    private Boolean lastDeactivationBySuperAdmin;

    @OneToMany(mappedBy = "service")
    @JsonIgnore
    private Set<Window> windows = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BranchService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartingTime() {
        return startingTime;
    }

    public BranchService startingTime(Instant startingTime) {
        this.startingTime = startingTime;
        return this;
    }

    public void setStartingTime(Instant startingTime) {
        this.startingTime = startingTime;
    }

    public Instant getEndingTime() {
        return endingTime;
    }

    public BranchService endingTime(Instant endingTime) {
        this.endingTime = endingTime;
        return this;
    }

    public void setEndingTime(Instant endingTime) {
        this.endingTime = endingTime;
    }

    public Double getAverageServingMinutes() {
        return averageServingMinutes;
    }

    public BranchService averageServingMinutes(Double averageServingMinutes) {
        this.averageServingMinutes = averageServingMinutes;
        return this;
    }

    public void setAverageServingMinutes(Double averageServingMinutes) {
        this.averageServingMinutes = averageServingMinutes;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public BranchService createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BranchService createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean isActive() {
        return active;
    }

    public BranchService active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isLastDeactivationBySuperAdmin() {
        return lastDeactivationBySuperAdmin;
    }

    public BranchService lastDeactivationBySuperAdmin(Boolean lastDeactivationBySuperAdmin) {
        this.lastDeactivationBySuperAdmin = lastDeactivationBySuperAdmin;
        return this;
    }

    public void setLastDeactivationBySuperAdmin(Boolean lastDeactivationBySuperAdmin) {
        this.lastDeactivationBySuperAdmin = lastDeactivationBySuperAdmin;
    }

    public Set<Window> getWindows() {
        return windows;
    }

    public BranchService windows(Set<Window> windows) {
        this.windows = windows;
        return this;
    }

    public BranchService addWindows(Window window) {
        this.windows.add(window);
        window.setService(this);
        return this;
    }

    public BranchService removeWindows(Window window) {
        this.windows.remove(window);
        window.setService(null);
        return this;
    }

    public void setWindows(Set<Window> windows) {
        this.windows = windows;
    }

    public Branch getBranch() {
        return branch;
    }

    public BranchService branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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
        BranchService branchService = (BranchService) o;
        if (branchService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), branchService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BranchService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startingTime='" + getStartingTime() + "'" +
            ", endingTime='" + getEndingTime() + "'" +
            ", averageServingMinutes='" + getAverageServingMinutes() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", active='" + isActive() + "'" +
            ", lastDeactivationBySuperAdmin='" + isLastDeactivationBySuperAdmin() + "'" +
            "}";
    }
}
