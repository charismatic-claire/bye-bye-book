package org.byebyebook.bbbserver.service;

import org.byebyebook.bbbserver.exception.ResourceNotFoundException;
import org.byebyebook.bbbserver.model.ImageUri;
import org.byebyebook.bbbserver.repository.ImageUriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageUriService {

    @Autowired
    private ImageUriRepository repository;

    public List<ImageUri> getAll() {
        return repository.findAll();
    }

    public ImageUri getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image uri with id <" + id + ">"));
    }

}
