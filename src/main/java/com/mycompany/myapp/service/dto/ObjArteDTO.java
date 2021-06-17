package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ObjArte} entity.
 */
public class ObjArteDTO implements Serializable {

    private Long id;

    @NotNull
    private String idObjArt;

    @NotNull
    private String paisCultura;

    private String anio;

    @NotNull
    private String tituloObj;

    @NotNull
    private String descObj;

    @NotNull
    private String epocaObj;

    private Set<ArtistaDTO> artistas = new HashSet<>();

    private ExhibicionDTO exhibicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdObjArt() {
        return idObjArt;
    }

    public void setIdObjArt(String idObjArt) {
        this.idObjArt = idObjArt;
    }

    public String getPaisCultura() {
        return paisCultura;
    }

    public void setPaisCultura(String paisCultura) {
        this.paisCultura = paisCultura;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTituloObj() {
        return tituloObj;
    }

    public void setTituloObj(String tituloObj) {
        this.tituloObj = tituloObj;
    }

    public String getDescObj() {
        return descObj;
    }

    public void setDescObj(String descObj) {
        this.descObj = descObj;
    }

    public String getEpocaObj() {
        return epocaObj;
    }

    public void setEpocaObj(String epocaObj) {
        this.epocaObj = epocaObj;
    }

    public Set<ArtistaDTO> getArtistas() {
        return artistas;
    }

    public void setArtistas(Set<ArtistaDTO> artistas) {
        this.artistas = artistas;
    }

    public ExhibicionDTO getExhibicion() {
        return exhibicion;
    }

    public void setExhibicion(ExhibicionDTO exhibicion) {
        this.exhibicion = exhibicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjArteDTO)) {
            return false;
        }

        ObjArteDTO objArteDTO = (ObjArteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, objArteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObjArteDTO{" +
            "id=" + getId() +
            ", idObjArt='" + getIdObjArt() + "'" +
            ", paisCultura='" + getPaisCultura() + "'" +
            ", anio='" + getAnio() + "'" +
            ", tituloObj='" + getTituloObj() + "'" +
            ", descObj='" + getDescObj() + "'" +
            ", epocaObj='" + getEpocaObj() + "'" +
            ", artistas=" + getArtistas() +
            ", exhibicion=" + getExhibicion() +
            "}";
    }
}
