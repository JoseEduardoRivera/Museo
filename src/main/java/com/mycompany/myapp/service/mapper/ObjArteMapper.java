package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ObjArteDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ObjArte} and its DTO {@link ObjArteDTO}.
 */
@Mapper(componentModel = "spring", uses = { ArtistaMapper.class, ExhibicionMapper.class })
public interface ObjArteMapper extends EntityMapper<ObjArteDTO, ObjArte> {
    @Mapping(target = "artistas", source = "artistas", qualifiedByName = "nomArtSet")
    @Mapping(target = "exhibicion", source = "exhibicion", qualifiedByName = "nomExi")
    ObjArteDTO toDto(ObjArte s);

    @Mapping(target = "removeArtista", ignore = true)
    ObjArte toEntity(ObjArteDTO objArteDTO);

    @Named("idObjArt")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idObjArt", source = "idObjArt")
    ObjArteDTO toDtoIdObjArt(ObjArte objArte);
}
