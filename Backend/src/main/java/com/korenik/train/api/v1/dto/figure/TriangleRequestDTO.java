package com.korenik.train.api.v1.dto.figure;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class TriangleRequestDTO extends FigureRequestDTO {

    @NotNull
    @Pattern(regexp = "^#([A-F]|[a-f]|[0-9]){6}$")
    private String colorHex;

}
