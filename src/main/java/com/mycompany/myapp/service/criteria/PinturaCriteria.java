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
 * Criteria class for the {@link com.mycompany.myapp.domain.Pintura} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PinturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pinturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PinturaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoPintura;

    private StringFilter materialPintura;

    private StringFilter estiloPint;

    private LongFilter objArteId;

    public PinturaCriteria() {}

    public PinturaCriteria(PinturaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoPintura = other.tipoPintura == null ? null : other.tipoPintura.copy();
        this.materialPintura = other.materialPintura == null ? null : other.materialPintura.copy();
        this.estiloPint = other.estiloPint == null ? null : other.estiloPint.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public PinturaCriteria copy() {
        return new PinturaCriteria(this);
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

    public StringFilter getTipoPintura() {
        return tipoPintura;
    }

    public StringFilter tipoPintura() {
        if (tipoPintura == null) {
            tipoPintura = new StringFilter();
        }
        return tipoPintura;
    }

    public void setTipoPintura(StringFilter tipoPintura) {
        this.tipoPintura = tipoPintura;
    }

    public StringFilter getMaterialPintura() {
        return materialPintura;
    }

    public StringFilter materialPintura() {
        if (materialPintura == null) {
            materialPintura = new StringFilter();
        }
        return materialPintura;
    }

    public void setMaterialPintura(StringFilter materialPintura) {
        this.materialPintura = materialPintura;
    }

    public StringFilter getEstiloPint() {
        return estiloPint;
    }

    public StringFilter estiloPint() {
        if (estiloPint == null) {
            estiloPint = new StringFilter();
        }
        return estiloPint;
    }

    public void setEstiloPint(StringFilter estiloPint) {
        this.estiloPint = estiloPint;
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
        final PinturaCriteria that = (PinturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tipoPintura, that.tipoPintura) &&
            Objects.equals(materialPintura, that.materialPintura) &&
            Objects.equals(estiloPint, that.estiloPint) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoPintura, materialPintura, estiloPint, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PinturaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tipoPintura != null ? "tipoPintura=" + tipoPintura + ", " : "") +
            (materialPintura != null ? "materialPintura=" + materialPintura + ", " : "") +
            (estiloPint != null ? "estiloPint=" + estiloPint + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
