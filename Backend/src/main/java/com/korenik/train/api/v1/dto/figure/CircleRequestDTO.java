package com.korenik.train.api.v1.dto.figure;

import com.korenik.train.model.BorderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircleRequestDTO extends FigureRequestDTO {

    private BorderType borderType;

}
