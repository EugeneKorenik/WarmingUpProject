package com.korenik.train.api.v1.dto.figure;

import com.korenik.train.model.BorderType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CircleRequestDTO extends FigureRequestDTO {

    @NotNull
    private BorderType borderType;

}
