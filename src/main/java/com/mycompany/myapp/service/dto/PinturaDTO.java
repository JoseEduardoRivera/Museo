package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Pintura} entity.
 */
public class PinturaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipoPintura;

    @NotNull
    private String materialPintura;

    @NotNull
    private String estiloPint;

    private ObjArteDTO objArte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPintura() {
        return tipoPintura;
    }

    public void setTipoPintura(String tipoPintura) {
        this.tipoPintura = tipoPintura;
    }

    public String getMaterialPintura() {
        return materialPintura;
    }

    public void setMaterialPintura(String materialPintura) {
        this.materialPintura = materialPintura;
    }

    public String getEstiloPint() {
        return estiloPint;
    }

    public void setEstiloPint(String estiloPint) {
        this.estiloPint = estiloPint;
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
        if (!(o instanceof PinturaDTO)) {
            return false;
        }

        PinturaDTO pinturaDTO = (PinturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pinturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PinturaDTO{" +
            "id=" + getId() +
            ", tipoPintura='" + getTipoPintura() + "'" +
            ", materialPintura='" + getMaterialPintura() + "'" +
            ", estiloPint='" + getEstiloPint() + "'" +
            ", objArte=" + getObjArte() +
            "}";
    }
}
