package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.PhotoTag;
import br.edu.ifpb.pweb2.flashg.entity.PhotoTagId;
import br.edu.ifpb.pweb2.flashg.entity.Tag;
import br.edu.ifpb.pweb2.flashg.repository.PhotoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoTagService {

    @Autowired
    private PhotoTagRepository repository;

    public List<PhotoTag> findByPhotoId(Long photoId) {
        return repository.findByPhotoId(photoId);
    }

    public List<PhotoTag> findByTagId(Long tagId) {
        return repository.findByTagId(tagId);
    }

    public PhotoTag create(PhotoTag photoTag) {
        // Verifica se a relação já existe
        Optional<PhotoTag> existingPhotoTag = repository.findById(photoTag.getId());

        // Se não existir, salva um novo
        // Se já existir, retorna o existente
        return existingPhotoTag.orElseGet(() -> repository.save(photoTag));

    }


    public void delete(PhotoTag photoTag) {
        repository.delete(photoTag);
    }

}
