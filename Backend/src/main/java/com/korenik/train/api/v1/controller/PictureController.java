package com.korenik.train.api.v1.controller;

import com.korenik.train.api.v1.dto.picture.PictureRequestDTO;
import com.korenik.train.api.v1.dto.picture.PictureResponseDTO;
import com.korenik.train.api.v1.mapper.PictureMapper;
import com.korenik.train.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pictures")
public class PictureController {

    private final PictureService pictureService;
    private final PictureMapper pictureMapper;

    @Autowired
    public PictureController(PictureService pictureService,
                             PictureMapper pictureMapper) {
        this.pictureService = pictureService;
        this.pictureMapper = pictureMapper;
    }

    @GetMapping
    public ResponseEntity<List<PictureResponseDTO>> findAll() {
        log.info("Request to find all pictures");
        var pictures = pictureService.findAll();
        var responseBody = pictureMapper.asResponse(pictures);
        responseBody.forEach(picture -> picture.setRootGroup(null));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureResponseDTO> findById(@PathVariable long id) {
        log.info("Request to find picture with id: {}", id);
        var entity = pictureService.findById(id);
        var responseBody = pictureMapper.asResponse(entity);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    public ResponseEntity<PictureResponseDTO> create(@RequestBody PictureRequestDTO request) {
        log.info("Request to create new picture");
        var entity = pictureMapper.asEntity(request);
        entity = pictureService.save(entity);
        var responseBody = pictureMapper.asResponse(entity);
        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PictureResponseDTO> update(@PathVariable long id,
                                                     @RequestBody PictureRequestDTO request) {
        log.info("Request to update picture information");

        var entity = pictureMapper.asEntity(request);
        entity.setId(id);
        entity = pictureService.update(entity);

        var response = pictureMapper.asResponse(entity);
        response.setRootGroup(null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info("Request to delete picture with id: {}", id);
        pictureService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
