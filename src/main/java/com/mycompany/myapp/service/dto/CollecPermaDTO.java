package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CollecPerma} entity.
 */
public class CollecPermaDTO implements Serializable {

    private Long id;

    @NotNull
    private String exhibiAlmacen;

    @NotNull
    private String costo;

    @NotNull
    private String fecha;

    private ObjArteDTO objArte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExhibiAlmacen() {
        return exhibiAlmacen;
    }

    public void setExhibiAlmacen(String exhibiAlmacen) {
        this.exhibiAlmacen = exhibiAlmacen;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
        if (!(o instanceof CollecPermaDTO)) {
            return false;
        }

        CollecPermaDTO collecPermaDTO = (CollecPermaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, collecPermaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPermaDTO{" +
            "id=" + getId() +
            ", exhibiAlmacen='" + getExhibiAlmacen() + "'" +
            ", costo='" + getCosto() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", objArte=" + getObjArte() +
            "}";
    }
}
