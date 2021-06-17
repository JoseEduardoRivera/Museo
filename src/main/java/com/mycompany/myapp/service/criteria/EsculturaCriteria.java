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
 * Criteria class for the {@link com.mycompany.myapp.domain.Escultura} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EsculturaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /esculturas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EsculturaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter altura;

    private StringFilter material;

    private StringFilter estilo;

    private StringFilter peso;

    private LongFilter objArteId;

    public EsculturaCriteria() {}

    public EsculturaCriteria(EsculturaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.altura = other.altura == null ? null : other.altura.copy();
        this.material = other.material == null ? null : other.material.copy();
        this.estilo = other.estilo == null ? null : other.estilo.copy();
        this.peso = other.peso == null ? null : other.peso.copy();
        this.objArteId = other.objArteId == null ? null : other.objArteId.copy();
    }

    @Override
    public EsculturaCriteria copy() {
        return new EsculturaCriteria(this);
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

    public StringFilter getAltura() {
        return altura;
    }

    public StringFilter altura() {
        if (altura == null) {
            altura = new StringFilter();
        }
        return altura;
    }

    public void setAltura(StringFilter altura) {
        this.altura = altura;
    }

    public StringFilter getMaterial() {
        return material;
    }

    public StringFilter material() {
        if (material == null) {
            material = new StringFilter();
        }
        return material;
    }

    public void setMaterial(StringFilter material) {
        this.material = material;
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

    public StringFilter getPeso() {
        return peso;
    }

    public StringFilter peso() {
        if (peso == null) {
            peso = new StringFilter();
        }
        return peso;
    }

    public void setPeso(StringFilter peso) {
        this.peso = peso;
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
        final EsculturaCriteria that = (EsculturaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(altura, that.altura) &&
            Objects.equals(material, that.material) &&
            Objects.equals(estilo, that.estilo) &&
            Objects.equals(peso, that.peso) &&
            Objects.equals(objArteId, that.objArteId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, altura, material, estilo, peso, objArteId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EsculturaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (altura != null ? "altura=" + altura + ", " : "") +
            (material != null ? "material=" + material + ", " : "") +
            (estilo != null ? "estilo=" + estilo + ", " : "") +
            (peso != null ? "peso=" + peso + ", " : "") +
            (objArteId != null ? "objArteId=" + objArteId + ", " : "") +
            "}";
    }
}
