package com.mindeavors.queuen.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Window.
 */
@Entity
@Table(name = "jhi_window")
public class Window implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private Agent currentAgent;

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

    public String getName() {
        return name;
    }

    public Window name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Window description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agent getCurrentAgent() {
        return currentAgent;
    }

    public Window currentAgent(Agent agent) {
        this.currentAgent = agent;
        return this;
    }

    public void setCurrentAgent(Agent agent) {
        this.currentAgent = agent;
    }

    public BranchService getService() {
        return service;
    }

    public Window service(BranchService branchService) {
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
        Window window = (Window) o;
        if (window.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), window.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Window{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
