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

import com.mindeavors.queuen.domain.enumeration.Day;

/**
 * A Branch.
 */
@Entity
@Table(name = "branch")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_working_hours")
    private Instant startWorkingHours;

    @Column(name = "end_working_hours")
    private Instant endWorkingHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "working_days")
    private Day workingDays;

    @NotNull
    @Column(name = "accepted_parallel_reservation_number", nullable = false)
    private Integer acceptedParallelReservationNumber;

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

    @OneToMany(mappedBy = "branch")
    @JsonIgnore
    private Set<BranchService> services = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartWorkingHours() {
        return startWorkingHours;
    }

    public Branch startWorkingHours(Instant startWorkingHours) {
        this.startWorkingHours = startWorkingHours;
        return this;
    }

    public void setStartWorkingHours(Instant startWorkingHours) {
        this.startWorkingHours = startWorkingHours;
    }

    public Instant getEndWorkingHours() {
        return endWorkingHours;
    }

    public Branch endWorkingHours(Instant endWorkingHours) {
        this.endWorkingHours = endWorkingHours;
        return this;
    }

    public void setEndWorkingHours(Instant endWorkingHours) {
        this.endWorkingHours = endWorkingHours;
    }

    public Day getWorkingDays() {
        return workingDays;
    }

    public Branch workingDays(Day workingDays) {
        this.workingDays = workingDays;
        return this;
    }

    public void setWorkingDays(Day workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getAcceptedParallelReservationNumber() {
        return acceptedParallelReservationNumber;
    }

    public Branch acceptedParallelReservationNumber(Integer acceptedParallelReservationNumber) {
        this.acceptedParallelReservationNumber = acceptedParallelReservationNumber;
        return this;
    }

    public void setAcceptedParallelReservationNumber(Integer acceptedParallelReservationNumber) {
        this.acceptedParallelReservationNumber = acceptedParallelReservationNumber;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Branch createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Branch createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean isActive() {
        return active;
    }

    public Branch active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isLastDeactivationBySuperAdmin() {
        return lastDeactivationBySuperAdmin;
    }

    public Branch lastDeactivationBySuperAdmin(Boolean lastDeactivationBySuperAdmin) {
        this.lastDeactivationBySuperAdmin = lastDeactivationBySuperAdmin;
        return this;
    }

    public void setLastDeactivationBySuperAdmin(Boolean lastDeactivationBySuperAdmin) {
        this.lastDeactivationBySuperAdmin = lastDeactivationBySuperAdmin;
    }

    public Set<BranchService> getServices() {
        return services;
    }

    public Branch services(Set<BranchService> branchServices) {
        this.services = branchServices;
        return this;
    }

    public Branch addServices(BranchService branchService) {
        this.services.add(branchService);
        branchService.setBranch(this);
        return this;
    }

    public Branch removeServices(BranchService branchService) {
        this.services.remove(branchService);
        branchService.setBranch(null);
        return this;
    }

    public void setServices(Set<BranchService> branchServices) {
        this.services = branchServices;
    }

    public Company getCompany() {
        return company;
    }

    public Branch company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Branch branch = (Branch) o;
        if (branch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), branch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Branch{" +
            "id=" + getId() +
            ", startWorkingHours='" + getStartWorkingHours() + "'" +
            ", endWorkingHours='" + getEndWorkingHours() + "'" +
            ", workingDays='" + getWorkingDays() + "'" +
            ", acceptedParallelReservationNumber='" + getAcceptedParallelReservationNumber() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", active='" + isActive() + "'" +
            ", lastDeactivationBySuperAdmin='" + isLastDeactivationBySuperAdmin() + "'" +
            "}";
    }
}
