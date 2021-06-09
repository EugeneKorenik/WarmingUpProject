package com.korenik.train.api.v1.dto.group;

import com.korenik.train.api.v1.dto.BaseResponseDTO;
import com.korenik.train.api.v1.dto.figure.FigureRequestDTO;
import com.korenik.train.api.v1.dto.figure.FigureResponseDTO;
import com.korenik.train.model.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupResponseDTO extends BaseResponseDTO {

    private Long parentGroupId;

    private GroupType groupType;

    private List<GroupResponseDTO> groups;

    private List<FigureResponseDTO> figures;

}
