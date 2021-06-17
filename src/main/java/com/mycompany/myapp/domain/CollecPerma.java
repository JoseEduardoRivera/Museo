package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CollecPerma.
 */
@Entity
@Table(name = "collec_perma")
public class CollecPerma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "exhibi_almacen", nullable = false)
    private String exhibiAlmacen;

    @NotNull
    @Column(name = "costo", nullable = false)
    private String costo;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private String fecha;

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

    public CollecPerma id(Long id) {
        this.id = id;
        return this;
    }

    public String getExhibiAlmacen() {
        return this.exhibiAlmacen;
    }

    public CollecPerma exhibiAlmacen(String exhibiAlmacen) {
        this.exhibiAlmacen = exhibiAlmacen;
        return this;
    }

    public void setExhibiAlmacen(String exhibiAlmacen) {
        this.exhibiAlmacen = exhibiAlmacen;
    }

    public String getCosto() {
        return this.costo;
    }

    public CollecPerma costo(String costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return this.fecha;
    }

    public CollecPerma fecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ObjArte getObjArte() {
        return this.objArte;
    }

    public CollecPerma objArte(ObjArte objArte) {
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
        if (!(o instanceof CollecPerma)) {
            return false;
        }
        return id != null && id.equals(((CollecPerma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPerma{" +
            "id=" + getId() +
            ", exhibiAlmacen='" + getExhibiAlmacen() + "'" +
            ", costo='" + getCosto() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
