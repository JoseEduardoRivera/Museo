package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A OtroObj.
 */
@Entity
@Table(name = "otro_obj")
public class OtroObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull
    @Column(name = "estilo", nullable = false)
    private String estilo;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "collecPermas", "collecPrestas", "pinturas", "esculturas", "otroObjs", "artistas", "exhibicion" },
        allowSetters = true
    )
    private ObjArte objArte;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OtroObj id(Long id) {
        this.id = id;
        return this;
    }

    public String getTipo() {
        return this.tipo;
    }

    public OtroObj tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public OtroObj estilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public ObjArte getObjArte() {
        return this.objArte;
    }

    public OtroObj objArte(ObjArte objArte) {
        this.setObjArte(objArte);
        return this;
    }

    public void setObjArte(ObjArte objArte) {
        this.objArte = objArte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtroObj)) {
            return false;
        }
        return id != null && id.equals(((OtroObj) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtroObj{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", estilo='" + getEstilo() + "'" +
            "}";
    }
}
