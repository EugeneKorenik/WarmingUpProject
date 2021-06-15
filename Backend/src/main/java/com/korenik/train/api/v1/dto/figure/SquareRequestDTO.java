package com.korenik.train.api.v1.dto.figure;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SquareRequestDTO extends FigureRequestDTO {

    @Pattern(regexp = "^([A-Z]|[0-9])$")
    private String symbol;

}
