package com.mindeavors.queuen.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SuperAdmin.
 */
@Entity
@Table(name = "super_admin")
public class SuperAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        SuperAdmin superAdmin = (SuperAdmin) o;
        if (superAdmin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), superAdmin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SuperAdmin{" +
            "id=" + getId() +
            "}";
    }
}
