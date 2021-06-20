package com.korenik.train.service.mapper;

import com.korenik.train.entity.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PictureMapperService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "rootGroup", ignore = true)
    void merge(Picture newInfo, @MappingTarget Picture entity);

}
