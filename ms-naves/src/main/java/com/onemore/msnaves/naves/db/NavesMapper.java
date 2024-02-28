package com.onemore.msnaves.naves.db;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NavesMapper {
	
    NavesMapper INSTANCE = Mappers.getMapper(NavesMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    NaveDto toDto(Nave nave);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    Nave toEntity(NaveDto nave);
    
}
