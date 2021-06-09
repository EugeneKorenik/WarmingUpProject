package com.korenik.train.api.v1.mapper;

import com.korenik.train.api.v1.dto.group.GroupRequestDTO;
import com.korenik.train.api.v1.dto.group.GroupResponseDTO;
import com.korenik.train.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                FigureMapper.class
        })
public interface GroupMapper {

    Group asEntity(GroupRequestDTO request);

    @Mapping(source = "parentGroup.id", target = "parentGroupId")
    GroupResponseDTO asResponse(Group entity);

    List<GroupResponseDTO> asResponse(List<Group> entity);

}
