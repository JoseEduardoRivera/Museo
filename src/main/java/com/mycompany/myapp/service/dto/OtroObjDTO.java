package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.OtroObj} entity.
 */
public class OtroObjDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    @NotNull
    private String estilo;

    private ObjArteDTO objArte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public ObjArteDTO getObjArte() {
        return objArte;
    }

    public void setObjArte(ObjArteDTO objArte) {
        this.objArte = objArte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtroObjDTO)) {
            return false;
        }

        OtroObjDTO otroObjDTO = (OtroObjDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, otroObjDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtroObjDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", objArte=" + getObjArte() +
            "}";
    }
}
