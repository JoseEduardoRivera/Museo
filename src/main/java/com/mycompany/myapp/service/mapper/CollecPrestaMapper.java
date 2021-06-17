package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CollecPrestaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CollecPresta} and its DTO {@link CollecPrestaDTO}.
 */
@Mapper(componentModel = "spring", uses = { ObjArteMapper.class })
public interface CollecPrestaMapper extends EntityMapper<CollecPrestaDTO, CollecPresta> {
    @Mapping(target = "objArte", source = "objArte", qualifiedByName = "idObjArt")
    CollecPrestaDTO toDto(CollecPresta s);
}
