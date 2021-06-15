package com.korenik.train.api.v1.mapper;

import com.korenik.train.api.v1.dto.picture.PictureRequestDTO;
import com.korenik.train.api.v1.dto.picture.PictureResponseDTO;
import com.korenik.train.entity.Picture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                GroupMapper.class
        })
public interface PictureMapper {

    Picture asEntity(PictureRequestDTO requestDTO);

    PictureResponseDTO asResponse(Picture entity);

    List<PictureResponseDTO> asResponse(List<Picture> entities);

/*
    default void removeDuplicatedGroups(Picture entity, @MappingTarget PictureResponseDTO response) {
        var groupsThatHaveParent = findThatHaveParentGroup(entity);
        var rootGroup = response.getRootGroup();
        var responseGroups = rootGroup.getGroups();
        }
    }

    private Set<Long> findThatHaveParentGroup(Picture entity) {
        var groups = entity.getGroups();
        if(!CollectionUtils.isEmpty(groups)) {
            return groups.stream()
                    .flatMap(this::collectId)
                    .collect(Collectors.toSet());
        }
        return Set.of();
    }

    private Stream<Long> collectId(Group group) {
        var childrenGroups = group.getGroups();
        List<Long> idList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(childrenGroups)) {
            idList = childrenGroups.stream()
                    .map(Group::getId)
                    .collect(Collectors.toList());

            var nextChildrenIdList = childrenGroups.stream()
                    .flatMap(this::collectId)
                    .collect(Collectors.toList());

            idList.addAll(nextChildrenIdList);
        }

        return idList.stream();
    }
    */

}
