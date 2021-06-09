package com.korenik.train.service.mapper;

import com.korenik.train.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GroupMapperService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "figures", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    void merge(Group entityFromRequest, @MappingTarget Group currentEntity);

}
