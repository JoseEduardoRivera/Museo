package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Exhibicion.
 */
@Entity
@Table(name = "exhibicion")
public class Exhibicion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_exi", nullable = false, unique = true)
    private String nomExi;

    @NotNull
    @Column(name = "fech_ini", nullable = false)
    private String fechIni;

    @NotNull
    @Column(name = "fech_fin", nullable = false)
    private String fechFin;

    @OneToMany(mappedBy = "exhibicion")
    @JsonIgnoreProperties(
        value = { "collecPermas", "collecPrestas", "pinturas", "esculturas", "otroObjs", "artistas", "exhibicion" },
        allowSetters = true
    )
    private Set<ObjArte> objArtes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exhibicion id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomExi() {
        return this.nomExi;
    }

    public Exhibicion nomExi(String nomExi) {
        this.nomExi = nomExi;
        return this;
    }

    public void setNomExi(String nomExi) {
        this.nomExi = nomExi;
    }

    public String getFechIni() {
        return this.fechIni;
    }

    public Exhibicion fechIni(String fechIni) {
        this.fechIni = fechIni;
        return this;
    }

    public void setFechIni(String fechIni) {
        this.fechIni = fechIni;
    }

    public String getFechFin() {
        return this.fechFin;
    }

    public Exhibicion fechFin(String fechFin) {
        this.fechFin = fechFin;
        return this;
    }

    public void setFechFin(String fechFin) {
        this.fechFin = fechFin;
    }

    public Set<ObjArte> getObjArtes() {
        return this.objArtes;
    }

    public Exhibicion objArtes(Set<ObjArte> objArtes) {
        this.setObjArtes(objArtes);
        return this;
    }

    public Exhibicion addObjArte(ObjArte objArte) {
        this.objArtes.add(objArte);
        objArte.setExhibicion(this);
        return this;
    }

    public Exhibicion removeObjArte(ObjArte objArte) {
        this.objArtes.remove(objArte);
        objArte.setExhibicion(null);
        return this;
    }

    public void setObjArtes(Set<ObjArte> objArtes) {
        if (this.objArtes != null) {
            this.objArtes.forEach(i -> i.setExhibicion(null));
        }
        if (objArtes != null) {
            objArtes.forEach(i -> i.setExhibicion(this));
        }
        this.objArtes = objArtes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exhibicion)) {
            return false;
        }
        return id != null && id.equals(((Exhibicion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exhibicion{" +
            "id=" + getId() +
            ", nomExi='" + getNomExi() + "'" +
            ", fechIni='" + getFechIni() + "'" +
            ", fechFin='" + getFechFin() + "'" +
            "}";
    }
}
