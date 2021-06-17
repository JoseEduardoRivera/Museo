package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EsculturaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Escultura} and its DTO {@link EsculturaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ObjArteMapper.class })
public interface EsculturaMapper extends EntityMapper<EsculturaDTO, Escultura> {
    @Mapping(target = "objArte", source = "objArte", qualifiedByName = "idObjArt")
    EsculturaDTO toDto(Escultura s);
}
