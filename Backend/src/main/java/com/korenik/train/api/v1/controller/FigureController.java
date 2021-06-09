package com.korenik.train.api.v1.controller;

import com.korenik.train.api.v1.dto.figure.FigureRequestDTO;
import com.korenik.train.api.v1.dto.figure.FigureResponseDTO;
import com.korenik.train.api.v1.mapper.FigureMapper;
import com.korenik.train.model.FigureType;
import com.korenik.train.service.FigureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/figures")
public class FigureController {

    private final FigureService figureService;
    private final FigureMapper figureMapper;

    @Autowired
    public FigureController(FigureService figureService,
                            FigureMapper figureMapper) {
        this.figureService = figureService;
        this.figureMapper = figureMapper;
    }

    @GetMapping
    public ResponseEntity<List<FigureResponseDTO>> findAll() {
        log.info("Request to find all figures");
        var figures = figureService.findAll();
        var response = figureMapper.asResponse(figures);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<FigureResponseDTO> findByTypeAndId(@PathVariable FigureType type,
                                                             @PathVariable long id) {
        log.info("Request to find figure of {} type with id {}", type, id);
        var figure = figureService.findByTypeAndId(type, id);
        var response = figureMapper.asResponse(figure);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<FigureResponseDTO> create(@RequestParam long groupId,
                                                    @RequestBody @Valid FigureRequestDTO request) {
        log.info("Request to create figure in group {}", groupId);
        var entity = figureMapper.asEntity(request);
        entity = figureService.create(groupId, entity);
        var response = figureMapper.asResponse(entity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{type}/{id}")
    public ResponseEntity<FigureResponseDTO> update(@PathVariable FigureType type,
                                                    @PathVariable long id,
                                                    @RequestParam(required = false) Long groupId,
                                                    @RequestBody @Valid FigureRequestDTO request) {
        log.info("Request to update figure of type {} with id {}", type, id);

        var entity = figureMapper.asEntity(request);
        entity.setId(id);
        entity = figureService.update(entity, groupId);

        var response = figureMapper.asResponse(entity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{type}/{id}")
    public ResponseEntity<Void> delete(@PathVariable FigureType type,
                                       @PathVariable long id) {
        log.info("Request to delete figure of {} type with id {}", type, id);
        figureService.delete(type, id);
        return ResponseEntity.noContent().build();
    }

}
