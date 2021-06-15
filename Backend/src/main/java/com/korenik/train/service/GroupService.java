package com.korenik.train.service;

import com.korenik.train.entity.Figure;
import com.korenik.train.entity.Group;
import com.korenik.train.repository.GroupRepository;
import com.korenik.train.service.mapper.GroupMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final PictureService pictureService;
    private final GroupMapperService groupMapperService;

    @Autowired
    public GroupService(GroupRepository groupRepository,
                        PictureService pictureService,
                        GroupMapperService groupMapperService) {
        this.groupRepository = groupRepository;
        this.pictureService = pictureService;
        this.groupMapperService = groupMapperService;
    }

    public Group findById(long id) {
        return groupRepository.getOne(id);
    }

    public Group create(Long pictureId,
                        Long parentGroupId,
                        Group group) {
        var picture = pictureService.findById(pictureId);
        group.setPicture(picture);
        group = groupRepository.save(group);

        var parentGroup = picture.getRootGroup();
        if(parentGroupId != null) {
            parentGroup = findById(parentGroupId);
        }

        bindToGroup(group, parentGroup);
        groupRepository.save(parentGroup);

        return group;
    }

    public Group update(Group group, Long newParentGroupId) {
        var originalEntity = findById(group.getId());

        if(newParentGroupId != null) {
            var newParentGroup = findById(newParentGroupId);
            bindToGroup(originalEntity, newParentGroup);
        }

        groupMapperService.merge(group, originalEntity);
        return groupRepository.save(originalEntity);
    }

    public void deleteById(long id) {
        var group = findById(id);
        unbindFromParent(group);
        groupRepository.delete(group);
    }

    private void bindToGroup(Group group, Group parentGroup) {
        unbindFromParent(group);

        group.setParentGroup(parentGroup);
        var childrenGroups = parentGroup.getGroups();
        if(childrenGroups == null) {
            childrenGroups = new ArrayList<>();
        }

        childrenGroups.add(group);
    }

    private void unbindFromParent(Group group) {
        var parentGroup = group.getParentGroup();
        if(parentGroup != null) {
            parentGroup.getGroups().remove(group);
            groupRepository.save(parentGroup);
        }
    }

}