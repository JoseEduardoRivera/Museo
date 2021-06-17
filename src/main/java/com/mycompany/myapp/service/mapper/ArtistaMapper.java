package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ArtistaDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artista} and its DTO {@link ArtistaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArtistaMapper extends EntityMapper<ArtistaDTO, Artista> {
    @Named("nomArtSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomArt", source = "nomArt")
    Set<ArtistaDTO> toDtoNomArtSet(Set<Artista> artista);
}
