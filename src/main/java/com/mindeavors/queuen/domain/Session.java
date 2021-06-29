package com.mindeavors.queuen.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.mindeavors.queuen.domain.enumeration.SessionStatus;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "rate_value")
    private Integer rateValue;

    @Lob
    @Column(name = "review_description")
    private String reviewDescription;

    @NotNull
    @Column(name = "start_date_time", nullable = false)
    private ZonedDateTime startDateTime;

    @NotNull
    @Column(name = "end_date_time", nullable = false)
    private ZonedDateTime endDateTime;

    @Column(name = "serving_minutes")
    private Double servingMinutes;

    @NotNull
    @Column(name = "window_name", nullable = false)
    private String windowName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SessionStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Agent agent;

    @ManyToOne
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRateValue() {
        return rateValue;
    }

    public Session rateValue(Integer rateValue) {
        this.rateValue = rateValue;
        return this;
    }

    public void setRateValue(Integer rateValue) {
        this.rateValue = rateValue;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public Session reviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
        return this;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public Session startDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public Session endDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Double getServingMinutes() {
        return servingMinutes;
    }

    public Session servingMinutes(Double servingMinutes) {
        this.servingMinutes = servingMinutes;
        return this;
    }

    public void setServingMinutes(Double servingMinutes) {
        this.servingMinutes = servingMinutes;
    }

    public String getWindowName() {
        return windowName;
    }

    public Session windowName(String windowName) {
        this.windowName = windowName;
        return this;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public Session status(SessionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Agent getAgent() {
        return agent;
    }

    public Session agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Session customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        Session session = (Session) o;
        if (session.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", rateValue='" + getRateValue() + "'" +
            ", reviewDescription='" + getReviewDescription() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", servingMinutes='" + getServingMinutes() + "'" +
            ", windowName='" + getWindowName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
