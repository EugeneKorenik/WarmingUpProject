package com.korenik.train.api.v1.dto.figure;

import com.korenik.train.model.GroupType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupResponseDTO extends FigureResponseDTO {

    private GroupType groupType;

    private List<FigureResponseDTO> figures;

}
