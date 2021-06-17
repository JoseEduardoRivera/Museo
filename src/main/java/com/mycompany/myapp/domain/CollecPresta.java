package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CollecPresta.
 */
@Entity
@Table(name = "collec_presta")
public class CollecPresta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "inf_prest", nullable = false)
    private String infPrest;

    @NotNull
    @Column(name = "fech_prest", nullable = false)
    private String fechPrest;

    @NotNull
    @Column(name = "fech_dev", nullable = false)
    private String fechDev;

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

    public CollecPresta id(Long id) {
        this.id = id;
        return this;
    }

    public String getInfPrest() {
        return this.infPrest;
    }

    public CollecPresta infPrest(String infPrest) {
        this.infPrest = infPrest;
        return this;
    }

    public void setInfPrest(String infPrest) {
        this.infPrest = infPrest;
    }

    public String getFechPrest() {
        return this.fechPrest;
    }

    public CollecPresta fechPrest(String fechPrest) {
        this.fechPrest = fechPrest;
        return this;
    }

    public void setFechPrest(String fechPrest) {
        this.fechPrest = fechPrest;
    }

    public String getFechDev() {
        return this.fechDev;
    }

    public CollecPresta fechDev(String fechDev) {
        this.fechDev = fechDev;
        return this;
    }

    public void setFechDev(String fechDev) {
        this.fechDev = fechDev;
    }

    public ObjArte getObjArte() {
        return this.objArte;
    }

    public CollecPresta objArte(ObjArte objArte) {
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
        if (!(o instanceof CollecPresta)) {
            return false;
        }
        return id != null && id.equals(((CollecPresta) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPresta{" +
            "id=" + getId() +
            ", infPrest='" + getInfPrest() + "'" +
            ", fechPrest='" + getFechPrest() + "'" +
            ", fechDev='" + getFechDev() + "'" +
            "}";
    }
}
