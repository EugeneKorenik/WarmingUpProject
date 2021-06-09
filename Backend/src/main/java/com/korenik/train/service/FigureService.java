package com.korenik.train.service;

import com.korenik.train.entity.Circle;
import com.korenik.train.entity.Figure;
import com.korenik.train.entity.Square;
import com.korenik.train.entity.Triangle;
import com.korenik.train.model.FigureType;
import com.korenik.train.service.mapper.FigureMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FigureService {

    private final JpaRepository<Circle, Long> circleRepository;
    private final JpaRepository<Triangle, Long> triangleRepository;
    private final JpaRepository<Square, Long> squareRepository;

    private final GroupService groupService;
    private final FigureMapperService figureMapperService;

    @Autowired
    public FigureService(JpaRepository<Circle, Long> circleRepository,
                         JpaRepository<Triangle, Long> triangleRepository,
                         JpaRepository<Square, Long> squareRepository,
                         GroupService groupService,
                         FigureMapperService figureMapperService) {
        this.circleRepository = circleRepository;
        this.triangleRepository = triangleRepository;
        this.squareRepository = squareRepository;
        this.groupService = groupService;
        this.figureMapperService = figureMapperService;
    }

    public List<Figure> findAll() {
        var allFigures = new ArrayList<Figure>();
        allFigures.addAll(circleRepository.findAll());
        allFigures.addAll(triangleRepository.findAll());
        allFigures.addAll(squareRepository.findAll());
        return allFigures;
    }

    public Figure findByTypeAndId(FigureType type, long id) {
        var repository = findRepositoryByType(type);
        return repository.getOne(id);
    }

    public Figure create(long groupId, Figure figure) {
        var repository = findRepositoryByClass(figure);
        var group = groupService.findById(groupId);
        figure.setParentGroup(group);
        return repository.save(figure);
    }

    public Figure update(Figure figure, Long newGroupId) {
        var repository = findRepositoryByClass(figure);
        var entity = repository.getOne(figure.getId());

        if(newGroupId != null) {
            var newParent = groupService.findById(newGroupId);
            entity.setParentGroup(newParent);
        }

        figureMapperService.merge(figure, entity);
        return repository.save(entity);
    }

    public void delete(FigureType figureType, long id) {
        var repository = findRepositoryByType(figureType);
        repository.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Figure, Long> findRepositoryByClass(Figure figure) {
        JpaRepository<? extends Figure, Long> requiredRepository;
        if(figure instanceof Circle) {
            requiredRepository = circleRepository;
        } else if(figure instanceof Triangle) {
            requiredRepository = triangleRepository;
        } else if(figure instanceof Square) {
            requiredRepository = squareRepository;
        } else {
            throw new IllegalArgumentException("Can't find repository for class: "
                    + figure.getClass().getName());
        }
        return (JpaRepository<Figure, Long>) requiredRepository;
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Figure, Long> findRepositoryByType(FigureType type) {
        JpaRepository<? extends Figure, Long> requiredRepository;
        if(type == FigureType.CIRCLE) {
            requiredRepository = circleRepository;
        } else if(type == FigureType.TRIANGLE) {
            requiredRepository = triangleRepository;
        } else if(type == FigureType.SQUARE) {;
            requiredRepository = squareRepository;
        } else {
            throw new IllegalArgumentException("Can't find repository for class: "
                    + type);
        }
        return (JpaRepository<Figure, Long>) requiredRepository;
    }

}
