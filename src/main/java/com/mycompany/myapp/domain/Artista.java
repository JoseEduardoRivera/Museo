package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Artista.
 */
@Entity
@Table(name = "artista")
public class Artista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_art", nullable = false, unique = true)
    private String nomArt;

    @NotNull
    @Column(name = "fech_nac", nullable = false)
    private String fechNac;

    @Column(name = "fech_defu")
    private String fechDefu;

    @NotNull
    @Column(name = "pais_origen", nullable = false)
    private String paisOrigen;

    @NotNull
    @Column(name = "estilo_art", nullable = false)
    private String estiloArt;

    @NotNull
    @Column(name = "epoca", nullable = false)
    private String epoca;

    @ManyToMany(mappedBy = "artistas")
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

    public Artista id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomArt() {
        return this.nomArt;
    }

    public Artista nomArt(String nomArt) {
        this.nomArt = nomArt;
        return this;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getFechNac() {
        return this.fechNac;
    }

    public Artista fechNac(String fechNac) {
        this.fechNac = fechNac;
        return this;
    }

    public void setFechNac(String fechNac) {
        this.fechNac = fechNac;
    }

    public String getFechDefu() {
        return this.fechDefu;
    }

    public Artista fechDefu(String fechDefu) {
        this.fechDefu = fechDefu;
        return this;
    }

    public void setFechDefu(String fechDefu) {
        this.fechDefu = fechDefu;
    }

    public String getPaisOrigen() {
        return this.paisOrigen;
    }

    public Artista paisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
        return this;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getEstiloArt() {
        return this.estiloArt;
    }

    public Artista estiloArt(String estiloArt) {
        this.estiloArt = estiloArt;
        return this;
    }

    public void setEstiloArt(String estiloArt) {
        this.estiloArt = estiloArt;
    }

    public String getEpoca() {
        return this.epoca;
    }

    public Artista epoca(String epoca) {
        this.epoca = epoca;
        return this;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public Set<ObjArte> getObjArtes() {
        return this.objArtes;
    }

    public Artista objArtes(Set<ObjArte> objArtes) {
        this.setObjArtes(objArtes);
        return this;
    }

    public Artista addObjArte(ObjArte objArte) {
        this.objArtes.add(objArte);
        objArte.getArtistas().add(this);
        return this;
    }

    public Artista removeObjArte(ObjArte objArte) {
        this.objArtes.remove(objArte);
        objArte.getArtistas().remove(this);
        return this;
    }

    public void setObjArtes(Set<ObjArte> objArtes) {
        if (this.objArtes != null) {
            this.objArtes.forEach(i -> i.removeArtista(this));
        }
        if (objArtes != null) {
            objArtes.forEach(i -> i.addArtista(this));
        }
        this.objArtes = objArtes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artista)) {
            return false;
        }
        return id != null && id.equals(((Artista) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artista{" +
            "id=" + getId() +
            ", nomArt='" + getNomArt() + "'" +
            ", fechNac='" + getFechNac() + "'" +
            ", fechDefu='" + getFechDefu() + "'" +
            ", paisOrigen='" + getPaisOrigen() + "'" +
            ", estiloArt='" + getEstiloArt() + "'" +
            ", epoca='" + getEpoca() + "'" +
            "}";
    }
}
