package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PinturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pintura} and its DTO {@link PinturaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ObjArteMapper.class })
public interface PinturaMapper extends EntityMapper<PinturaDTO, Pintura> {
    @Mapping(target = "objArte", source = "objArte", qualifiedByName = "idObjArt")
    PinturaDTO toDto(Pintura s);
}
