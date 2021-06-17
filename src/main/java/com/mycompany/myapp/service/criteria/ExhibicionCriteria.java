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
 * Criteria class for the {@link com.mycompany.myapp.domain.Exhibicion} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ExhibicionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exhibicions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExhibicionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomExi;

    private StringFilter fechIni;

    private StringFilter fechFin;

    private LongFilter objArteId;

    public ExhibicionCriteria() {}

    public ExhibicionCriteria(ExhibicionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomExi = other.nomExi == null ? null : other.nomExi.copy();
        this.fechIni = other.fechIni == null ? null : other.fechIni.copy();
        this.fechFin = other.fechFin == null ? null : other.fechFin.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public ExhibicionCriteria copy() {
        return new ExhibicionCriteria(this);
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

    public StringFilter getNomExi() {
        return nomExi;
    }

    public StringFilter nomExi() {
        if (nomExi == null) {
            nomExi = new StringFilter();
        }
        return nomExi;
    }

    public void setNomExi(StringFilter nomExi) {
        this.nomExi = nomExi;
    }

    public StringFilter getFechIni() {
        return fechIni;
    }

    public StringFilter fechIni() {
        if (fechIni == null) {
            fechIni = new StringFilter();
        }
        return fechIni;
    }

    public void setFechIni(StringFilter fechIni) {
        this.fechIni = fechIni;
    }

    public StringFilter getFechFin() {
        return fechFin;
    }

    public StringFilter fechFin() {
        if (fechFin == null) {
            fechFin = new StringFilter();
        }
        return fechFin;
    }

    public void setFechFin(StringFilter fechFin) {
        this.fechFin = fechFin;
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
        final ExhibicionCriteria that = (ExhibicionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomExi, that.nomExi) &&
            Objects.equals(fechIni, that.fechIni) &&
            Objects.equals(fechFin, that.fechFin) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomExi, fechIni, fechFin, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExhibicionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomExi != null ? "nomExi=" + nomExi + ", " : "") +
            (fechIni != null ? "fechIni=" + fechIni + ", " : "") +
            (fechFin != null ? "fechFin=" + fechFin + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
