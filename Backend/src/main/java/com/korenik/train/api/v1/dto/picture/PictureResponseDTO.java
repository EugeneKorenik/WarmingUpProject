package com.korenik.train.api.v1.dto.picture;

import com.korenik.train.api.v1.dto.BaseResponseDTO;
import com.korenik.train.api.v1.dto.figure.GroupResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PictureResponseDTO extends BaseResponseDTO {

    private String name;

    private GroupResponseDTO rootGroup;

}
