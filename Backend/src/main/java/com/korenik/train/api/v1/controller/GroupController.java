package com.korenik.train.api.v1.controller;

import com.korenik.train.api.v1.dto.group.GroupRequestDTO;
import com.korenik.train.api.v1.dto.group.GroupResponseDTO;
import com.korenik.train.api.v1.mapper.GroupMapper;
import com.korenik.train.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupController(GroupService groupService,
                           GroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> findById(@PathVariable long id) {
        log.info("Request to find group with id: {}", id);
        var entity = groupService.findById(id);
        var response = groupMapper.asResponse(entity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> create(@RequestParam Long pictureId,
                                                   @RequestParam(required = false) Long parentGroupId,
                                                   @RequestBody GroupRequestDTO request) {
        log.info("Request to create new group for picture {} under group {}", pictureId, parentGroupId);

        var entity = groupMapper.asEntity(request);
        entity = groupService.create(pictureId, parentGroupId, entity);

        var response = groupMapper.asResponse(entity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> update(@PathVariable long id,
                                                   @RequestParam(required = false) Long parentGroupId,
                                                   @RequestBody GroupRequestDTO request) {
        log.info("Request to update group with id: {}", id);

        var entity = groupMapper.asEntity(request);
        entity.setId(id);
        entity = groupService.update(entity, parentGroupId);

        var response = groupMapper.asResponse(entity);
        response.setGroups(null);
        response.setFigures(null);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        log.info("Request to delete group with id: {}", id);
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
