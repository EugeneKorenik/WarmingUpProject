package com.korenik.train.api.v1.mapper;

import com.korenik.train.api.v1.dto.figure.*;
import com.korenik.train.api.v1.dto.figure.CircleRequestDTO;
import com.korenik.train.api.v1.dto.figure.FigureRequestDTO;
import com.korenik.train.entity.Circle;
import com.korenik.train.entity.Figure;
import com.korenik.train.entity.Square;
import com.korenik.train.entity.Triangle;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FigureMapper {

    List<FigureResponseDTO> asResponse(List<Figure> entities);

    default Figure asEntity(FigureRequestDTO request) {
        if (request instanceof CircleRequestDTO) {
            return asEntity((CircleRequestDTO) request);
        } else if (request instanceof TriangleRequestDTO) {
            return asEntity((TriangleRequestDTO) request);
        } else if (request instanceof SquareRequestDTO) {
            return asEntity((SquareRequestDTO) request);
        }

        throw new IllegalArgumentException("Can't process instance of class: "
                + request.getClass().getName());
    }

    default FigureResponseDTO asResponse(Figure entity) {
        if (entity instanceof Circle) {
            return asDTO((Circle) entity);
        } else if (entity instanceof Triangle) {
            return asDTO((Triangle) entity);
        } else if (entity instanceof Square) {
            return asDTO((Square) entity);
        }

        throw new IllegalArgumentException("Can't process instance of class: "
                + entity.getClass().getName());
    }

    Circle asEntity(CircleRequestDTO request);

    CircleResponseDTO asDTO(Circle entity);

    Triangle asEntity(TriangleRequestDTO request);

    TriangleResponseDTO asDTO(Triangle entity);

    Square asEntity(SquareRequestDTO request);

    SquareResponseDTO asDTO(Square square);

}
