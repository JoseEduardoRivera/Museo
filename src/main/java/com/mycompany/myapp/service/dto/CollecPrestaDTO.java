package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CollecPresta} entity.
 */
public class CollecPrestaDTO implements Serializable {

    private Long id;

    @NotNull
    private String infPrest;

    @NotNull
    private String fechPrest;

    @NotNull
    private String fechDev;

    private ObjArteDTO objArte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfPrest() {
        return infPrest;
    }

    public void setInfPrest(String infPrest) {
        this.infPrest = infPrest;
    }

    public String getFechPrest() {
        return fechPrest;
    }

    public void setFechPrest(String fechPrest) {
        this.fechPrest = fechPrest;
    }

    public String getFechDev() {
        return fechDev;
    }

    public void setFechDev(String fechDev) {
        this.fechDev = fechDev;
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
        if (!(o instanceof CollecPrestaDTO)) {
            return false;
        }

        CollecPrestaDTO collecPrestaDTO = (CollecPrestaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, collecPrestaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollecPrestaDTO{" +
            "id=" + getId() +
            ", infPrest='" + getInfPrest() + "'" +
            ", fechPrest='" + getFechPrest() + "'" +
            ", fechDev='" + getFechDev() + "'" +
            ", objArte=" + getObjArte() +
            "}";
    }
}
