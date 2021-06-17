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
 * Criteria class for the {@link com.mycompany.myapp.domain.CollecPresta} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CollecPrestaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /collec-prestas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CollecPrestaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter infPrest;

    private StringFilter fechPrest;

    private StringFilter fechDev;

    private LongFilter objArteId;

    public CollecPrestaCriteria() {}

    public CollecPrestaCriteria(CollecPrestaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.infPrest = other.infPrest == null ? null : other.infPrest.copy();
        this.fechPrest = other.fechPrest == null ? null : other.fechPrest.copy();
        this.fechDev = other.fechDev == null ? null : other.fechDev.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public CollecPrestaCriteria copy() {
        return new CollecPrestaCriteria(this);
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

    public StringFilter getInfPrest() {
        return infPrest;
    }

    public StringFilter infPrest() {
        if (infPrest == null) {
            infPrest = new StringFilter();
        }
        return infPrest;
    }

    public void setInfPrest(StringFilter infPrest) {
        this.infPrest = infPrest;
    }

    public StringFilter getFechPrest() {
        return fechPrest;
    }

    public StringFilter fechPrest() {
        if (fechPrest == null) {
            fechPrest = new StringFilter();
        }
        return fechPrest;
    }

    public void setFechPrest(StringFilter fechPrest) {
        this.fechPrest = fechPrest;
    }

    public StringFilter getFechDev() {
        return fechDev;
    }

    public StringFilter fechDev() {
        if (fechDev == null) {
            fechDev = new StringFilter();
        }
        return fechDev;
    }

    public void setFechDev(StringFilter fechDev) {
        this.fechDev = fechDev;
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
        final CollecPrestaCriteria that = (CollecPrestaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(infPrest, that.infPrest) &&
            Objects.equals(fechPrest, that.fechPrest) &&
            Objects.equals(fechDev, that.fechDev) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, infPrest, fechPrest, fechDev, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPrestaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (infPrest != null ? "infPrest=" + infPrest + ", " : "") +
            (fechPrest != null ? "fechPrest=" + fechPrest + ", " : "") +
            (fechDev != null ? "fechDev=" + fechDev + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
