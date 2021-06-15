package com.korenik.train.api.v1.dto.figure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CircleResponseDTO.class, name = "circle"),
        @JsonSubTypes.Type(value = TriangleResponseDTO.class, name = "triangle"),
        @JsonSubTypes.Type(value = SquareResponseDTO.class, name = "square"),
        @JsonSubTypes.Type(value = GroupResponseDTO.class, name = "group")
})
public abstract class FigureResponseDTO {

    private long id;

    private Long parentGroupId;

    private LocalDateTime created;

    private LocalDateTime modified;

}
