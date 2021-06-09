package com.korenik.train.api.v1.dto.figure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CircleRequestDTO.class, name = "circle"),
        @JsonSubTypes.Type(value = TriangleRequestDTO.class, name = "triangle"),
        @JsonSubTypes.Type(value = SquareRequestDTO.class, name = "square")
})
public abstract class FigureRequestDTO {
}
