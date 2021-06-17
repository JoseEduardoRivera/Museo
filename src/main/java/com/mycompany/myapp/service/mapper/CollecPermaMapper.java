package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CollecPermaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CollecPerma} and its DTO {@link CollecPermaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ObjArteMapper.class })
public interface CollecPermaMapper extends EntityMapper<CollecPermaDTO, CollecPerma> {
    @Mapping(target = "objArte", source = "objArte", qualifiedByName = "idObjArt")
    CollecPermaDTO toDto(CollecPerma s);
}
