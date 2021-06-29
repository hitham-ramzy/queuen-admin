package com.mindeavors.queuen.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mindeavors.queuen.domain.enumeration.QueueStatus;

/**
 * A Queue.
 */
@Entity
@Table(name = "queue")
public class Queue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private QueueStatus status;

    @NotNull
    @Column(name = "day", nullable = false)
    private ZonedDateTime day;

    @NotNull
    @Column(name = "current_serving_number", nullable = false)
    private Integer currentServingNumber;

    @NotNull
    @Column(name = "last_reserved_number", nullable = false)
    private Integer lastReservedNumber;

    @Column(name = "last_status_by_super_admin")
    private Boolean lastStatusBySuperAdmin;

    @ManyToOne(optional = false)
    @NotNull
    private BranchService service;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public Queue status(QueueStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }

    public ZonedDateTime getDay() {
        return day;
    }

    public Queue day(ZonedDateTime day) {
        this.day = day;
        return this;
    }

    public void setDay(ZonedDateTime day) {
        this.day = day;
    }

    public Integer getCurrentServingNumber() {
        return currentServingNumber;
    }

    public Queue currentServingNumber(Integer currentServingNumber) {
        this.currentServingNumber = currentServingNumber;
        return this;
    }

    public void setCurrentServingNumber(Integer currentServingNumber) {
        this.currentServingNumber = currentServingNumber;
    }

    public Integer getLastReservedNumber() {
        return lastReservedNumber;
    }

    public Queue lastReservedNumber(Integer lastReservedNumber) {
        this.lastReservedNumber = lastReservedNumber;
        return this;
    }

    public void setLastReservedNumber(Integer lastReservedNumber) {
        this.lastReservedNumber = lastReservedNumber;
    }

    public Boolean isLastStatusBySuperAdmin() {
        return lastStatusBySuperAdmin;
    }

    public Queue lastStatusBySuperAdmin(Boolean lastStatusBySuperAdmin) {
        this.lastStatusBySuperAdmin = lastStatusBySuperAdmin;
        return this;
    }

    public void setLastStatusBySuperAdmin(Boolean lastStatusBySuperAdmin) {
        this.lastStatusBySuperAdmin = lastStatusBySuperAdmin;
    }

    public BranchService getService() {
        return service;
    }

    public Queue service(BranchService branchService) {
        this.service = branchService;
        return this;
    }

    public void setService(BranchService branchService) {
        this.service = branchService;
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
        Queue queue = (Queue) o;
        if (queue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), queue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Queue{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", day='" + getDay() + "'" +
            ", currentServingNumber='" + getCurrentServingNumber() + "'" +
            ", lastReservedNumber='" + getLastReservedNumber() + "'" +
            ", lastStatusBySuperAdmin='" + isLastStatusBySuperAdmin() + "'" +
            "}";
    }
}
