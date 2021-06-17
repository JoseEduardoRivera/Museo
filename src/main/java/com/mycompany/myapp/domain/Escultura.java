package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Escultura.
 */
@Entity
@Table(name = "escultura")
public class Escultura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "altura", nullable = false)
    private String altura;

    @NotNull
    @Column(name = "material", nullable = false)
    private String material;

    @NotNull
    @Column(name = "estilo", nullable = false)
    private String estilo;

    @NotNull
    @Column(name = "peso", nullable = false)
    private String peso;

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

    public Escultura id(Long id) {
        this.id = id;
        return this;
    }

    public String getAltura() {
        return this.altura;
    }

    public Escultura altura(String altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getMaterial() {
        return this.material;
    }

    public Escultura material(String material) {
        this.material = material;
        return this;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public Escultura estilo(String estilo) {
        this.estilo = estilo;
        return this;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getPeso() {
        return this.peso;
    }

    public Escultura peso(String peso) {
        this.peso = peso;
        return this;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public ObjArte getObjArte() {
        return this.objArte;
    }

    public Escultura objArte(ObjArte objArte) {
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
        if (!(o instanceof Escultura)) {
            return false;
        }
        return id != null && id.equals(((Escultura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Escultura{" +
            "id=" + getId() +
            ", altura='" + getAltura() + "'" +
            ", material='" + getMaterial() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", peso='" + getPeso() + "'" +
            "}";
    }
}
