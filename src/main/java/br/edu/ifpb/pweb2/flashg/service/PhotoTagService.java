package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.PhotoTag;
import br.edu.ifpb.pweb2.flashg.entity.PhotoTagId;
import br.edu.ifpb.pweb2.flashg.entity.Tag;
import br.edu.ifpb.pweb2.flashg.repository.PhotoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PhotoTag create(Tag tag , Photo photo) {

        PhotoTag photoTag = new PhotoTag();
        photoTag.setTag(tag);
        photoTag.setPhoto(photo);

        return repository.save(photoTag);
    }

    public void delete(PhotoTag photoTag) {
        repository.delete(photoTag);
    }

}
