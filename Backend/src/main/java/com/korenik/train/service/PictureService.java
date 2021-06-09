package com.korenik.train.service;

import com.korenik.train.entity.Picture;
import com.korenik.train.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    public Picture findById(long id) {
        return pictureRepository.getOne(id);
    }

    public Picture save(Picture picture) {
        return pictureRepository.save(picture);
    }

    public void deleteById(long id) {
        pictureRepository.deleteById(id);
    }

}
