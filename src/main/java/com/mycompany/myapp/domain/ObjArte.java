package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ObjArte.
 */
@Entity
@Table(name = "obj_arte")
public class ObjArte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "id_obj_art", nullable = false, unique = true)
    private String idObjArt;

    @NotNull
    @Column(name = "pais_cultura", nullable = false)
    private String paisCultura;

    @Column(name = "anio")
    private String anio;

    @NotNull
    @Column(name = "titulo_obj", nullable = false)
    private String tituloObj;

    @NotNull
    @Column(name = "desc_obj", nullable = false)
    private String descObj;

    @NotNull
    @Column(name = "epoca_obj", nullable = false)
    private String epocaObj;

    @OneToMany(mappedBy = "objArte")
    @JsonIgnoreProperties(value = { "objArte" }, allowSetters = true)
    private Set<CollecPerma> collecPermas = new HashSet<>();

    @OneToMany(mappedBy = "objArte")
    @JsonIgnoreProperties(value = { "objArte" }, allowSetters = true)
    private Set<CollecPresta> collecPrestas = new HashSet<>();

    @OneToMany(mappedBy = "objArte")
    @JsonIgnoreProperties(value = { "objArte" }, allowSetters = true)
    private Set<Pintura> pinturas = new HashSet<>();

    @OneToMany(mappedBy = "objArte")
    @JsonIgnoreProperties(value = { "objArte" }, allowSetters = true)
    private Set<Escultura> esculturas = new HashSet<>();

    @OneToMany(mappedBy = "objArte")
    @JsonIgnoreProperties(value = { "objArte" }, allowSetters = true)
    private Set<OtroObj> otroObjs = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_obj_arte__artista",
        joinColumns = @JoinColumn(name = "obj_arte_id"),
        inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    @JsonIgnoreProperties(value = { "objArtes" }, allowSetters = true)
    private Set<Artista> artistas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "objArtes" }, allowSetters = true)
    private Exhibicion exhibicion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ObjArte id(Long id) {
        this.id = id;
        return this;
    }

    public String getIdObjArt() {
        return this.idObjArt;
    }

    public ObjArte idObjArt(String idObjArt) {
        this.idObjArt = idObjArt;
        return this;
    }

    public void setIdObjArt(String idObjArt) {
        this.idObjArt = idObjArt;
    }

    public String getPaisCultura() {
        return this.paisCultura;
    }

    public ObjArte paisCultura(String paisCultura) {
        this.paisCultura = paisCultura;
        return this;
    }

    public void setPaisCultura(String paisCultura) {
        this.paisCultura = paisCultura;
    }

    public String getAnio() {
        return this.anio;
    }

    public ObjArte anio(String anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTituloObj() {
        return this.tituloObj;
    }

    public ObjArte tituloObj(String tituloObj) {
        this.tituloObj = tituloObj;
        return this;
    }

    public void setTituloObj(String tituloObj) {
        this.tituloObj = tituloObj;
    }

    public String getDescObj() {
        return this.descObj;
    }

    public ObjArte descObj(String descObj) {
        this.descObj = descObj;
        return this;
    }

    public void setDescObj(String descObj) {
        this.descObj = descObj;
    }

    public String getEpocaObj() {
        return this.epocaObj;
    }

    public ObjArte epocaObj(String epocaObj) {
        this.epocaObj = epocaObj;
        return this;
    }

    public void setEpocaObj(String epocaObj) {
        this.epocaObj = epocaObj;
    }

    public Set<CollecPerma> getCollecPermas() {
        return this.collecPermas;
    }

    public ObjArte collecPermas(Set<CollecPerma> collecPermas) {
        this.setCollecPermas(collecPermas);
        return this;
    }

    public ObjArte addCollecPerma(CollecPerma collecPerma) {
        this.collecPermas.add(collecPerma);
        collecPerma.setObjArte(this);
        return this;
    }

    public ObjArte removeCollecPerma(CollecPerma collecPerma) {
        this.collecPermas.remove(collecPerma);
        collecPerma.setObjArte(null);
        return this;
    }

    public void setCollecPermas(Set<CollecPerma> collecPermas) {
        if (this.collecPermas != null) {
            this.collecPermas.forEach(i -> i.setObjArte(null));
        }
        if (collecPermas != null) {
            collecPermas.forEach(i -> i.setObjArte(this));
        }
        this.collecPermas = collecPermas;
    }

    public Set<CollecPresta> getCollecPrestas() {
        return this.collecPrestas;
    }

    public ObjArte collecPrestas(Set<CollecPresta> collecPrestas) {
        this.setCollecPrestas(collecPrestas);
        return this;
    }

    public ObjArte addCollecPresta(CollecPresta collecPresta) {
        this.collecPrestas.add(collecPresta);
        collecPresta.setObjArte(this);
        return this;
    }

    public ObjArte removeCollecPresta(CollecPresta collecPresta) {
        this.collecPrestas.remove(collecPresta);
        collecPresta.setObjArte(null);
        return this;
    }

    public void setCollecPrestas(Set<CollecPresta> collecPrestas) {
        if (this.collecPrestas != null) {
            this.collecPrestas.forEach(i -> i.setObjArte(null));
        }
        if (collecPrestas != null) {
            collecPrestas.forEach(i -> i.setObjArte(this));
        }
        this.collecPrestas = collecPrestas;
    }

    public Set<Pintura> getPinturas() {
        return this.pinturas;
    }

    public ObjArte pinturas(Set<Pintura> pinturas) {
        this.setPinturas(pinturas);
        return this;
    }

    public ObjArte addPintura(Pintura pintura) {
        this.pinturas.add(pintura);
        pintura.setObjArte(this);
        return this;
    }

    public ObjArte removePintura(Pintura pintura) {
        this.pinturas.remove(pintura);
        pintura.setObjArte(null);
        return this;
    }

    public void setPinturas(Set<Pintura> pinturas) {
        if (this.pinturas != null) {
            this.pinturas.forEach(i -> i.setObjArte(null));
        }
        if (pinturas != null) {
            pinturas.forEach(i -> i.setObjArte(this));
        }
        this.pinturas = pinturas;
    }

    public Set<Escultura> getEsculturas() {
        return this.esculturas;
    }

    public ObjArte esculturas(Set<Escultura> esculturas) {
        this.setEsculturas(esculturas);
        return this;
    }

    public ObjArte addEscultura(Escultura escultura) {
        this.esculturas.add(escultura);
        escultura.setObjArte(this);
        return this;
    }

    public ObjArte removeEscultura(Escultura escultura) {
        this.esculturas.remove(escultura);
        escultura.setObjArte(null);
        return this;
    }

    public void setEsculturas(Set<Escultura> esculturas) {
        if (this.esculturas != null) {
            this.esculturas.forEach(i -> i.setObjArte(null));
        }
        if (esculturas != null) {
            esculturas.forEach(i -> i.setObjArte(this));
        }
        this.esculturas = esculturas;
    }

    public Set<OtroObj> getOtroObjs() {
        return this.otroObjs;
    }

    public ObjArte otroObjs(Set<OtroObj> otroObjs) {
        this.setOtroObjs(otroObjs);
        return this;
    }

    public ObjArte addOtroObj(OtroObj otroObj) {
        this.otroObjs.add(otroObj);
        otroObj.setObjArte(this);
        return this;
    }

    public ObjArte removeOtroObj(OtroObj otroObj) {
        this.otroObjs.remove(otroObj);
        otroObj.setObjArte(null);
        return this;
    }

    public void setOtroObjs(Set<OtroObj> otroObjs) {
        if (this.otroObjs != null) {
            this.otroObjs.forEach(i -> i.setObjArte(null));
        }
        if (otroObjs != null) {
            otroObjs.forEach(i -> i.setObjArte(this));
        }
        this.otroObjs = otroObjs;
    }

    public Set<Artista> getArtistas() {
        return this.artistas;
    }

    public ObjArte artistas(Set<Artista> artistas) {
        this.setArtistas(artistas);
        return this;
    }

    public ObjArte addArtista(Artista artista) {
        this.artistas.add(artista);
        artista.getObjArtes().add(this);
        return this;
    }

    public ObjArte removeArtista(Artista artista) {
        this.artistas.remove(artista);
        artista.getObjArtes().remove(this);
        return this;
    }

    public void setArtistas(Set<Artista> artistas) {
        this.artistas = artistas;
    }

    public Exhibicion getExhibicion() {
        return this.exhibicion;
    }

    public ObjArte exhibicion(Exhibicion exhibicion) {
        this.setExhibicion(exhibicion);
        return this;
    }

    public void setExhibicion(Exhibicion exhibicion) {
        this.exhibicion = exhibicion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjArte)) {
            return false;
        }
        return id != null && id.equals(((ObjArte) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjArte{" +
            "id=" + getId() +
            ", idObjArt='" + getIdObjArt() + "'" +
            ", paisCultura='" + getPaisCultura() + "'" +
            ", anio='" + getAnio() + "'" +
            ", tituloObj='" + getTituloObj() + "'" +
            ", descObj='" + getDescObj() + "'" +
            ", epocaObj='" + getEpocaObj() + "'" +
            "}";
    }
}
