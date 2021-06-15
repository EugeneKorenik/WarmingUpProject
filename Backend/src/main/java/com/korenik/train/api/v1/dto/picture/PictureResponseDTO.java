package com.korenik.train.api.v1.dto.picture;

import com.korenik.train.api.v1.dto.BaseResponseDTO;
import com.korenik.train.api.v1.dto.group.GroupResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PictureResponseDTO extends BaseResponseDTO {

    private String name;

    private GroupResponseDTO rootGroup;

}
