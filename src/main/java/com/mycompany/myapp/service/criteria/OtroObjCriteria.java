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
 * Criteria class for the {@link com.mycompany.myapp.domain.OtroObj} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OtroObjResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /otro-objs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OtroObjCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipo;

    private StringFilter estilo;

    private LongFilter objArteId;

    public OtroObjCriteria() {}

    public OtroObjCriteria(OtroObjCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.estilo = other.estilo == null ? null : other.estilo.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public OtroObjCriteria copy() {
        return new OtroObjCriteria(this);
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

    public StringFilter getTipo() {
        return tipo;
    }

    public StringFilter tipo() {
        if (tipo == null) {
            tipo = new StringFilter();
        }
        return tipo;
    }

    public void setTipo(StringFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getEstilo() {
        return estilo;
    }

    public StringFilter estilo() {
        if (estilo == null) {
            estilo = new StringFilter();
        }
        return estilo;
    }

    public void setEstilo(StringFilter estilo) {
        this.estilo = estilo;
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
        final OtroObjCriteria that = (OtroObjCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(estilo, that.estilo) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, estilo, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtroObjCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipo != null ? "tipo=" + tipo + ", " : "") +
            (estilo != null ? "estilo=" + estilo + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
