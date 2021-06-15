package com.korenik.train.api.v1.dto.figure;

import com.korenik.train.model.GroupType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequestDTO extends FigureRequestDTO {

    private GroupType groupType;

}
