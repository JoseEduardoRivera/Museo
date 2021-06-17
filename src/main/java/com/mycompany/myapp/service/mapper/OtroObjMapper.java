package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OtroObjDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OtroObj} and its DTO {@link OtroObjDTO}.
 */
@Mapper(componentModel = "spring", uses = { ObjArteMapper.class })
public interface OtroObjMapper extends EntityMapper<OtroObjDTO, OtroObj> {
    @Mapping(target = "objArte", source = "objArte", qualifiedByName = "idObjArt")
    OtroObjDTO toDto(OtroObj s);
}
