package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Artista} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ArtistaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /artistas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArtistaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomArt;

    private StringFilter fechNac;

    private StringFilter fechDefu;

    private StringFilter paisOrigen;

    private StringFilter estiloArt;

    private StringFilter epoca;

    private LongFilter objArteId;

    public ArtistaCriteria() {}

    public ArtistaCriteria(ArtistaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomArt = other.nomArt == null ? null : other.nomArt.copy();
        this.fechNac = other.fechNac == null ? null : other.fechNac.copy();
        this.fechDefu = other.fechDefu == null ? null : other.fechDefu.copy();
        this.paisOrigen = other.paisOrigen == null ? null : other.paisOrigen.copy();
        this.estiloArt = other.estiloArt == null ? null : other.estiloArt.copy();
        this.epoca = other.epoca == null ? null : other.epoca.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public ArtistaCriteria copy() {
        return new ArtistaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomArt() {
        return nomArt;
    }

    public StringFilter nomArt() {
        if (nomArt == null) {
            nomArt = new StringFilter();
        }
        return nomArt;
    }

    public void setNomArt(StringFilter nomArt) {
        this.nomArt = nomArt;
    }

    public StringFilter getFechNac() {
        return fechNac;
    }

    public StringFilter fechNac() {
        if (fechNac == null) {
            fechNac = new StringFilter();
        }
        return fechNac;
    }

    public void setFechNac(StringFilter fechNac) {
        this.fechNac = fechNac;
    }

    public StringFilter getFechDefu() {
        return fechDefu;
    }

    public StringFilter fechDefu() {
        if (fechDefu == null) {
            fechDefu = new StringFilter();
        }
        return fechDefu;
    }

    public void setFechDefu(StringFilter fechDefu) {
        this.fechDefu = fechDefu;
    }

    public StringFilter getPaisOrigen() {
        return paisOrigen;
    }

    public StringFilter paisOrigen() {
        if (paisOrigen == null) {
            paisOrigen = new StringFilter();
        }
        return paisOrigen;
    }

    public void setPaisOrigen(StringFilter paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public StringFilter getEstiloArt() {
        return estiloArt;
    }

    public StringFilter estiloArt() {
        if (estiloArt == null) {
            estiloArt = new StringFilter();
        }
        return estiloArt;
    }

    public void setEstiloArt(StringFilter estiloArt) {
        this.estiloArt = estiloArt;
    }

    public StringFilter getEpoca() {
        return epoca;
    }

    public StringFilter epoca() {
        if (epoca == null) {
            epoca = new StringFilter();
        }
        return epoca;
    }

    public void setEpoca(StringFilter epoca) {
        this.epoca = epoca;
    }

    public LongFilter getObjArteId() {
        return objArteId;
    }

    public LongFilter objArteId() {
        if (objArteId == null) {
            objArteId = new LongFilter();
        }
        return objArteId;
    }

    public void setObjArteId(LongFilter objArteId) {
        this.objArteId = objArteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArtistaCriteria that = (ArtistaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomArt, that.nomArt) &&
            Objects.equals(fechNac, that.fechNac) &&
            Objects.equals(fechDefu, that.fechDefu) &&
            Objects.equals(paisOrigen, that.paisOrigen) &&
            Objects.equals(estiloArt, that.estiloArt) &&
            Objects.equals(epoca, that.epoca) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomArt, fechNac, fechDefu, paisOrigen, estiloArt, epoca, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomArt != null ? "nomArt=" + nomArt + ", " : "") +
            (fechNac != null ? "fechNac=" + fechNac + ", " : "") +
            (fechDefu != null ? "fechDefu=" + fechDefu + ", " : "") +
            (paisOrigen != null ? "paisOrigen=" + paisOrigen + ", " : "") +
            (estiloArt != null ? "estiloArt=" + estiloArt + ", " : "") +
            (epoca != null ? "epoca=" + epoca + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
