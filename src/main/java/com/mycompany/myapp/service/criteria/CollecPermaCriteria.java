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
 * Criteria class for the {@link com.mycompany.myapp.domain.CollecPerma} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CollecPermaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /collec-permas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CollecPermaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter exhibiAlmacen;

    private StringFilter costo;

    private StringFilter fecha;

    private LongFilter objArteId;

    public CollecPermaCriteria() {}

    public CollecPermaCriteria(CollecPermaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.exhibiAlmacen = other.exhibiAlmacen == null ? null : other.exhibiAlmacen.copy();
        this.costo = other.costo == null ? null : other.costo.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public CollecPermaCriteria copy() {
        return new CollecPermaCriteria(this);
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

    public StringFilter getExhibiAlmacen() {
        return exhibiAlmacen;
    }

    public StringFilter exhibiAlmacen() {
        if (exhibiAlmacen == null) {
            exhibiAlmacen = new StringFilter();
        }
        return exhibiAlmacen;
    }

    public void setExhibiAlmacen(StringFilter exhibiAlmacen) {
        this.exhibiAlmacen = exhibiAlmacen;
    }

    public StringFilter getCosto() {
        return costo;
    }

    public StringFilter costo() {
        if (costo == null) {
            costo = new StringFilter();
        }
        return costo;
    }

    public void setCosto(StringFilter costo) {
        this.costo = costo;
    }

    public StringFilter getFecha() {
        return fecha;
    }

    public StringFilter fecha() {
        if (fecha == null) {
            fecha = new StringFilter();
        }
        return fecha;
    }

    public void setFecha(StringFilter fecha) {
        this.fecha = fecha;
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
        final CollecPermaCriteria that = (CollecPermaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(exhibiAlmacen, that.exhibiAlmacen) &&
            Objects.equals(costo, that.costo) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exhibiAlmacen, costo, fecha, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPermaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (exhibiAlmacen != null ? "exhibiAlmacen=" + exhibiAlmacen + ", " : "") +
            (costo != null ? "costo=" + costo + ", " : "") +
            (fecha != null ? "fecha=" + fecha + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
