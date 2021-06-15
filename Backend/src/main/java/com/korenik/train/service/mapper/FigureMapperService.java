package com.korenik.train.service.mapper;

import com.korenik.train.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FigureMapperService {

    default void merge(Figure newInfo, Figure entity) {
        if (newInfo instanceof Circle) {
            merge((Circle) newInfo, (Circle) entity);
        } else if (newInfo instanceof Triangle) {
            merge((Triangle) newInfo, (Triangle) entity);
        } else if (newInfo instanceof Square) {
            merge((Square) newInfo, (Square) entity);
        } else if(newInfo instanceof Group) {
            merge((Group) newInfo, (Group) entity);
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    void merge(Circle newInfo, @MappingTarget Circle entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    void merge(Triangle newInfo, @MappingTarget Triangle entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    void merge(Square newInfo, @MappingTarget Square entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentGroup", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "figures", ignore = true)
    void merge(Group newInfo, @MappingTarget Group entity);

}
