package com.korenik.train.api.v1.mapper;

import com.korenik.train.api.v1.dto.picture.PictureRequestDTO;
import com.korenik.train.api.v1.dto.picture.PictureResponseDTO;
import com.korenik.train.entity.Picture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
                FigureMapper.class
        })
public interface PictureMapper {

    Picture asEntity(PictureRequestDTO requestDTO);

    PictureResponseDTO asResponse(Picture entity);

    List<PictureResponseDTO> asResponse(List<Picture> entities);

}
