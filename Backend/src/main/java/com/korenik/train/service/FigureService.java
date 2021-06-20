package com.korenik.train.service;

import com.korenik.train.entity.*;
import com.korenik.train.model.ElementType;
import com.korenik.train.service.mapper.FigureMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class FigureService {

    private final JpaRepository<Circle, Long> circleRepository;
    private final JpaRepository<Triangle, Long> triangleRepository;
    private final JpaRepository<Square, Long> squareRepository;
    private final JpaRepository<Group, Long> groupRepository;

    private final FigureMapperService figureMapperService;

    @Autowired
    public FigureService(JpaRepository<Circle, Long> circleRepository,
                         JpaRepository<Triangle, Long> triangleRepository,
                         JpaRepository<Square, Long> squareRepository,
                         JpaRepository<Group, Long> groupRepository,
                         FigureMapperService figureMapperService) {
        this.circleRepository = circleRepository;
        this.triangleRepository = triangleRepository;
        this.squareRepository = squareRepository;
        this.groupRepository = groupRepository;
        this.figureMapperService = figureMapperService;
    }

    public List<Figure> findAll() {
        var allFigures = new ArrayList<Figure>();
        allFigures.addAll(circleRepository.findAll());
        allFigures.addAll(triangleRepository.findAll());
        allFigures.addAll(squareRepository.findAll());
        allFigures.addAll(groupRepository.findAll());
        return allFigures;
    }

    public Figure findByTypeAndId(Figure figure) {
        var repository = findRepositoryByClass(figure);
        return repository.findById(figure.getId()).orElseThrow(EntityNotFoundException::new);
    }

    public Figure findByTypeAndId(ElementType type, long id) {
        var repository = findRepositoryByType(type);
        return repository.getOne(id);
    }

    public Group findGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Figure create(Figure figure, long groupId, Integer index) {
        var repository = findRepositoryByClass(figure);
        updateParentGroup(figure, groupId);
        figure = repository.save(figure);
        updateIndex(figure, index);
        return figure;
    }

    public Figure update(Figure figure, Long newGroupId, Integer newIndex) {
        var repository = findRepositoryByClass(figure);
        var entity = findByTypeAndId(figure);

        updateParentGroup(entity, newGroupId);
        figureMapperService.merge(figure, entity);
        entity = repository.save(entity);

        updateIndex(entity, newIndex);
        return entity;
    }

    public Figure update(long id, ElementType type, Long newGroupId, Integer newIndex) {
        var repository = findRepositoryByType(type);
        var entity = findByTypeAndId(type, id);
        updateParentGroup(entity, newGroupId);
        updateIndex(entity, newIndex);
        return repository.save(entity);
    }

    public void delete(ElementType elementType, long id) {
        var repository = findRepositoryByType(elementType);
        repository.deleteById(id);
    }

    private void updateParentGroup(Figure entity, Long newGroupId) {
        var oldParentGroup = entity.getParentGroup();
        if(newGroupId != null && (oldParentGroup == null || oldParentGroup.getId() != newGroupId)) {
            if(oldParentGroup != null) {
                var parentGroupFigures = oldParentGroup.getFigures();
                parentGroupFigures.remove(entity);
                groupRepository.save(oldParentGroup);
            }

            var parent = findGroupById(newGroupId);
            entity.setParentGroup(parent);

            var figures = parent.getFigures();
            if(figures == null) {
                figures = new LinkedList<>();
            }

            figures.add(entity);
        }
    }

    private void updateIndex(Figure figure, Integer newIndex) {
        if(newIndex != null) {
            var parentGroup = figure.getParentGroup();
            var childrenFigures = parentGroup.getFigures();
            var normalizedIndex = normalizeIndex(newIndex, childrenFigures);
            childrenFigures.remove(figure);

            if(normalizedIndex == childrenFigures.size()) {
                childrenFigures.add(figure);
            } else {
                childrenFigures.add(normalizedIndex, figure);
            }

            groupRepository.save(parentGroup);
        }
    }

    private int normalizeIndex(Integer index, List<Figure> elements) {
        if(CollectionUtils.isEmpty(elements) || index < 0) {
            return 0;
        }

        if(index >= elements.size()) {
            return elements.size() - 1;
        }

        return index;
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
        } else if(figure instanceof Group) {
            requiredRepository = groupRepository;
        } else {
            throw new IllegalArgumentException("Can't find repository for class: "
                    + figure.getClass().getName());
        }
        return (JpaRepository<Figure, Long>) requiredRepository;
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<Figure, Long> findRepositoryByType(ElementType type) {
        JpaRepository<? extends Figure, Long> requiredRepository;
        if(type == ElementType.CIRCLE) {
            requiredRepository = circleRepository;
        } else if(type == ElementType.TRIANGLE) {
            requiredRepository = triangleRepository;
        } else if(type == ElementType.SQUARE) {
            requiredRepository = squareRepository;
        } else if(type == ElementType.GROUP) {
            requiredRepository = groupRepository;
        } else {
            throw new IllegalArgumentException("Can't find repository for class: "
                    + type);
        }

        return (JpaRepository<Figure, Long>) requiredRepository;
    }

}
