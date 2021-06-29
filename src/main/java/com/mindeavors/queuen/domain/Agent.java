package com.mindeavors.queuen.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "default_branch_service_id")
    private Long defaultBranchServiceId;

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

    public Long getDefaultBranchServiceId() {
        return defaultBranchServiceId;
    }

    public Agent defaultBranchServiceId(Long defaultBranchServiceId) {
        this.defaultBranchServiceId = defaultBranchServiceId;
        return this;
    }

    public void setDefaultBranchServiceId(Long defaultBranchServiceId) {
        this.defaultBranchServiceId = defaultBranchServiceId;
    }

    public Branch getBranch() {
        return branch;
    }

    public Agent branch(Branch branch) {
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
        Agent agent = (Agent) o;
        if (agent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", defaultBranchServiceId='" + getDefaultBranchServiceId() + "'" +
            "}";
    }
}
