package com.korenik.train.api.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseResponseDTO {

    private long id;

    private LocalDateTime created;

    private LocalDateTime modified;

}
