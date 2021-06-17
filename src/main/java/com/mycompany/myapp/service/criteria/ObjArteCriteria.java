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
 * Criteria class for the {@link com.mycompany.myapp.domain.ObjArte} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ObjArteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /obj-artes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ObjArteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idObjArt;

    private StringFilter paisCultura;

    private StringFilter anio;

    private StringFilter tituloObj;

    private StringFilter descObj;

    private StringFilter epocaObj;

    private LongFilter collecPermaId;

    private LongFilter collecPrestaId;

    private LongFilter pinturaId;

    private LongFilter esculturaId;

    private LongFilter otroObjId;

    private LongFilter artistaId;

    private LongFilter exhibicionId;

    public ObjArteCriteria() {}

    public ObjArteCriteria(ObjArteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idObjArt = other.idObjArt == null ? null : other.idObjArt.copy();
        this.paisCultura = other.paisCultura == null ? null : other.paisCultura.copy();
        this.anio = other.anio == null ? null : other.anio.copy();
        this.tituloObj = other.tituloObj == null ? null : other.tituloObj.copy();
        this.descObj = other.descObj == null ? null : other.descObj.copy();
        this.epocaObj = other.epocaObj == null ? null : other.epocaObj.copy();
        this.collecPermaId = other.collecPermaId == null ? null : other.collecPermaId.copy();
        this.collecPrestaId = other.collecPrestaId == null ? null : other.collecPrestaId.copy();
        this.pinturaId = other.pinturaId == null ? null : other.pinturaId.copy();
        this.esculturaId = other.esculturaId == null ? null : other.esculturaId.copy();
        this.otroObjId = other.otroObjId == null ? null : other.otroObjId.copy();
        this.artistaId = other.artistaId == null ? null : other.artistaId.copy();
        this.exhibicionId = other.exhibicionId == null ? null : other.exhibicionId.copy();
    }

    @Override
    public ObjArteCriteria copy() {
        return new ObjArteCriteria(this);
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

    public StringFilter getIdObjArt() {
        return idObjArt;
    }

    public StringFilter idObjArt() {
        if (idObjArt == null) {
            idObjArt = new StringFilter();
        }
        return idObjArt;
    }

    public void setIdObjArt(StringFilter idObjArt) {
        this.idObjArt = idObjArt;
    }

    public StringFilter getPaisCultura() {
        return paisCultura;
    }

    public StringFilter paisCultura() {
        if (paisCultura == null) {
            paisCultura = new StringFilter();
        }
        return paisCultura;
    }

    public void setPaisCultura(StringFilter paisCultura) {
        this.paisCultura = paisCultura;
    }

    public StringFilter getAnio() {
        return anio;
    }

    public StringFilter anio() {
        if (anio == null) {
            anio = new StringFilter();
        }
        return anio;
    }

    public void setAnio(StringFilter anio) {
        this.anio = anio;
    }

    public StringFilter getTituloObj() {
        return tituloObj;
    }

    public StringFilter tituloObj() {
        if (tituloObj == null) {
            tituloObj = new StringFilter();
        }
        return tituloObj;
    }

    public void setTituloObj(StringFilter tituloObj) {
        this.tituloObj = tituloObj;
    }

    public StringFilter getDescObj() {
        return descObj;
    }

    public StringFilter descObj() {
        if (descObj == null) {
            descObj = new StringFilter();
        }
        return descObj;
    }

    public void setDescObj(StringFilter descObj) {
        this.descObj = descObj;
    }

    public StringFilter getEpocaObj() {
        return epocaObj;
    }

    public StringFilter epocaObj() {
        if (epocaObj == null) {
            epocaObj = new StringFilter();
        }
        return epocaObj;
    }

    public void setEpocaObj(StringFilter epocaObj) {
        this.epocaObj = epocaObj;
    }

    public LongFilter getCollecPermaId() {
        return collecPermaId;
    }

    public LongFilter collecPermaId() {
        if (collecPermaId == null) {
            collecPermaId = new LongFilter();
        }
        return collecPermaId;
    }

    public void setCollecPermaId(LongFilter collecPermaId) {
        this.collecPermaId = collecPermaId;
    }

    public LongFilter getCollecPrestaId() {
        return collecPrestaId;
    }

    public LongFilter collecPrestaId() {
        if (collecPrestaId == null) {
            collecPrestaId = new LongFilter();
        }
        return collecPrestaId;
    }

    public void setCollecPrestaId(LongFilter collecPrestaId) {
        this.collecPrestaId = collecPrestaId;
    }

    public LongFilter getPinturaId() {
        return pinturaId;
    }

    public LongFilter pinturaId() {
        if (pinturaId == null) {
            pinturaId = new LongFilter();
        }
        return pinturaId;
    }

    public void setPinturaId(LongFilter pinturaId) {
        this.pinturaId = pinturaId;
    }

    public LongFilter getEsculturaId() {
        return esculturaId;
    }

    public LongFilter esculturaId() {
        if (esculturaId == null) {
            esculturaId = new LongFilter();
        }
        return esculturaId;
    }

    public void setEsculturaId(LongFilter esculturaId) {
        this.esculturaId = esculturaId;
    }

    public LongFilter getOtroObjId() {
        return otroObjId;
    }

    public LongFilter otroObjId() {
        if (otroObjId == null) {
            otroObjId = new LongFilter();
        }
        return otroObjId;
    }

    public void setOtroObjId(LongFilter otroObjId) {
        this.otroObjId = otroObjId;
    }

    public LongFilter getArtistaId() {
        return artistaId;
    }

    public LongFilter artistaId() {
        if (artistaId == null) {
            artistaId = new LongFilter();
        }
        return artistaId;
    }

    public void setArtistaId(LongFilter artistaId) {
        this.artistaId = artistaId;
    }

    public LongFilter getExhibicionId() {
        return exhibicionId;
    }

    public LongFilter exhibicionId() {
        if (exhibicionId == null) {
            exhibicionId = new LongFilter();
        }
        return exhibicionId;
    }

    public void setExhibicionId(LongFilter exhibicionId) {
        this.exhibicionId = exhibicionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ObjArteCriteria that = (ObjArteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idObjArt, that.idObjArt) &&
            Objects.equals(paisCultura, that.paisCultura) &&
            Objects.equals(anio, that.anio) &&
            Objects.equals(tituloObj, that.tituloObj) &&
            Objects.equals(descObj, that.descObj) &&
            Objects.equals(epocaObj, that.epocaObj) &&
            Objects.equals(collecPermaId, that.collecPermaId) &&
            Objects.equals(collecPrestaId, that.collecPrestaId) &&
            Objects.equals(pinturaId, that.pinturaId) &&
            Objects.equals(esculturaId, that.esculturaId) &&
            Objects.equals(otroObjId, that.otroObjId) &&
            Objects.equals(artistaId, that.artistaId) &&
            Objects.equals(exhibicionId, that.exhibicionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idObjArt,
            paisCultura,
            anio,
            tituloObj,
            descObj,
            epocaObj,
            collecPermaId,
            collecPrestaId,
            pinturaId,
            esculturaId,
            otroObjId,
            artistaId,
            exhibicionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjArteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idObjArt != null ? "idObjArt=" + idObjArt + ", " : "") +
            (paisCultura != null ? "paisCultura=" + paisCultura + ", " : "") +
            (anio != null ? "anio=" + anio + ", " : "") +
            (tituloObj != null ? "tituloObj=" + tituloObj + ", " : "") +
            (descObj != null ? "descObj=" + descObj + ", " : "") +
            (epocaObj != null ? "epocaObj=" + epocaObj + ", " : "") +
            (collecPermaId != null ? "collecPermaId=" + collecPermaId + ", " : "") +
            (collecPrestaId != null ? "collecPrestaId=" + collecPrestaId + ", " : "") +
            (pinturaId != null ? "pinturaId=" + pinturaId + ", " : "") +
            (esculturaId != null ? "esculturaId=" + esculturaId + ", " : "") +
            (otroObjId != null ? "otroObjId=" + otroObjId + ", " : "") +
            (artistaId != null ? "artistaId=" + artistaId + ", " : "") +
            (exhibicionId != null ? "exhibicionId=" + exhibicionId + ", " : "") +
            "}";
    }
}
