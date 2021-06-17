package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Artista} entity.
 */
public class ArtistaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomArt;

    @NotNull
    private String fechNac;

    private String fechDefu;

    @NotNull
    private String paisOrigen;

    @NotNull
    private String estiloArt;

    @NotNull
    private String epoca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public String getFechNac() {
        return fechNac;
    }

    public void setFechNac(String fechNac) {
        this.fechNac = fechNac;
    }

    public String getFechDefu() {
        return fechDefu;
    }

    public void setFechDefu(String fechDefu) {
        this.fechDefu = fechDefu;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getEstiloArt() {
        return estiloArt;
    }

    public void setEstiloArt(String estiloArt) {
        this.estiloArt = estiloArt;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistaDTO)) {
            return false;
        }

        ArtistaDTO artistaDTO = (ArtistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistaDTO{" +
            "id=" + getId() +
            ", nomArt='" + getNomArt() + "'" +
            ", fechNac='" + getFechNac() + "'" +
            ", fechDefu='" + getFechDefu() + "'" +
            ", paisOrigen='" + getPaisOrigen() + "'" +
            ", estiloArt='" + getEstiloArt() + "'" +
            ", epoca='" + getEpoca() + "'" +
            "}";
    }
}
