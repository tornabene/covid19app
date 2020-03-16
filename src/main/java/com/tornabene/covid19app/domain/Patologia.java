package com.tornabene.covid19app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patologia.
 */
@Entity
@Table(name = "patologia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patologia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "altrePatologies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Paziente> pazientis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Patologia nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Paziente> getPazientis() {
        return pazientis;
    }

    public Patologia pazientis(Set<Paziente> pazientes) {
        this.pazientis = pazientes;
        return this;
    }

    public Patologia addPazienti(Paziente paziente) {
        this.pazientis.add(paziente);
        paziente.getAltrePatologies().add(this);
        return this;
    }

    public Patologia removePazienti(Paziente paziente) {
        this.pazientis.remove(paziente);
        paziente.getAltrePatologies().remove(this);
        return this;
    }

    public void setPazientis(Set<Paziente> pazientes) {
        this.pazientis = pazientes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patologia)) {
            return false;
        }
        return id != null && id.equals(((Patologia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Patologia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
