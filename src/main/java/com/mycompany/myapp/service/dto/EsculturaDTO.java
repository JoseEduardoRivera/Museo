package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Escultura} entity.
 */
public class EsculturaDTO implements Serializable {

    private Long id;

    @NotNull
    private String altura;

    @NotNull
    private String material;

    @NotNull
    private String estilo;

    @NotNull
    private String peso;

    private ObjArteDTO objArte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
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
        if (!(o instanceof EsculturaDTO)) {
            return false;
        }

        EsculturaDTO esculturaDTO = (EsculturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, esculturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EsculturaDTO{" +
            "id=" + getId() +
            ", altura='" + getAltura() + "'" +
            ", material='" + getMaterial() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", peso='" + getPeso() + "'" +
            ", objArte=" + getObjArte() +
            "}";
    }
}
