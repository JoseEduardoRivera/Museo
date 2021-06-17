package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ExhibicionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exhibicion} and its DTO {@link ExhibicionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExhibicionMapper extends EntityMapper<ExhibicionDTO, Exhibicion> {
    @Named("nomExi")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomExi", source = "nomExi")
    ExhibicionDTO toDtoNomExi(Exhibicion exhibicion);
}
