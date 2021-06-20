package com.korenik.train.api.v1.dto.figure;

import com.korenik.train.model.BorderStyle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CircleResponseDTO extends FigureResponseDTO {

    private BorderStyle borderStyle;

}
