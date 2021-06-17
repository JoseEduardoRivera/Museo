package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Pintura.
 */
@Entity
@Table(name = "pintura")
public class Pintura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo_pintura", nullable = false)
    private String tipoPintura;

    @NotNull
    @Column(name = "material_pintura", nullable = false)
    private String materialPintura;

    @NotNull
    @Column(name = "estilo_pint", nullable = false)
    private String estiloPint;

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

    public Pintura id(Long id) {
        this.id = id;
        return this;
    }

    public String getTipoPintura() {
        return this.tipoPintura;
    }

    public Pintura tipoPintura(String tipoPintura) {
        this.tipoPintura = tipoPintura;
        return this;
    }

    public void setTipoPintura(String tipoPintura) {
        this.tipoPintura = tipoPintura;
    }

    public String getMaterialPintura() {
        return this.materialPintura;
    }

    public Pintura materialPintura(String materialPintura) {
        this.materialPintura = materialPintura;
        return this;
    }

    public void setMaterialPintura(String materialPintura) {
        this.materialPintura = materialPintura;
    }

    public String getEstiloPint() {
        return this.estiloPint;
    }

    public Pintura estiloPint(String estiloPint) {
        this.estiloPint = estiloPint;
        return this;
    }

    public void setEstiloPint(String estiloPint) {
        this.estiloPint = estiloPint;
    }

    public ObjArte getObjArte() {
        return this.objArte;
    }

    public Pintura objArte(ObjArte objArte) {
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
        if (!(o instanceof Pintura)) {
            return false;
        }
        return id != null && id.equals(((Pintura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pintura{" +
            "id=" + getId() +
            ", tipoPintura='" + getTipoPintura() + "'" +
            ", materialPintura='" + getMaterialPintura() + "'" +
            ", estiloPint='" + getEstiloPint() + "'" +
            "}";
    }
}
