package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Exhibicion} entity.
 */
public class ExhibicionDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomExi;

    @NotNull
    private String fechIni;

    @NotNull
    private String fechFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomExi() {
        return nomExi;
    }

    public void setNomExi(String nomExi) {
        this.nomExi = nomExi;
    }

    public String getFechIni() {
        return fechIni;
    }

    public void setFechIni(String fechIni) {
        this.fechIni = fechIni;
    }

    public String getFechFin() {
        return fechFin;
    }

    public void setFechFin(String fechFin) {
        this.fechFin = fechFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExhibicionDTO)) {
            return false;
        }

        ExhibicionDTO exhibicionDTO = (ExhibicionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exhibicionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExhibicionDTO{" +
            "id=" + getId() +
            ", nomExi='" + getNomExi() + "'" +
            ", fechIni='" + getFechIni() + "'" +
            ", fechFin='" + getFechFin() + "'" +
            "}";
    }
}
