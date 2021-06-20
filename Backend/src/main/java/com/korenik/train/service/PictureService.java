package com.korenik.train.service;

import com.korenik.train.entity.Group;
import com.korenik.train.entity.Picture;
import com.korenik.train.model.GroupType;
import com.korenik.train.repository.PictureRepository;
import com.korenik.train.service.mapper.PictureMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PictureMapperService pictureMapperService;

    @Autowired
    public PictureService(PictureRepository pictureRepository,
                          PictureMapperService pictureMapperService) {
        this.pictureRepository = pictureRepository;
        this.pictureMapperService = pictureMapperService;
    }

    public List<Picture> findAll() {
        return pictureRepository.findAllByOrderByModifiedDesc();
    }

    public Picture findById(long id) {
        return pictureRepository.getOne(id);
    }

    public Picture save(Picture picture) {
        createRootGroup(picture);
        return pictureRepository.save(picture);
    }

    public Picture update(Picture newInfo) {
        var entity = findById(newInfo.getId());
        pictureMapperService.merge(newInfo, entity);
        return pictureRepository.save(entity);
    }

    public void deleteById(long id) {
        pictureRepository.deleteById(id);
    }

    private void createRootGroup(Picture picture) {
        var rootGroup = new Group();
        rootGroup.setGroupType(GroupType.COLUMN);
        picture.setRootGroup(rootGroup);
        rootGroup.setPicture(picture);
    }
}
