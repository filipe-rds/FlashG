package br.edu.ifpb.pweb2.flashg.service;


import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository repository;

    public List<Photo>FindAll(){
        return repository.findAll();
    }

    public Optional<Photo> FindById(Long id){
        return repository.findById(id);
    }

    public Photo save(Photo photo){
        return repository.save(photo);
    }

    public Photo create(Photo photo, MultipartFile file) throws IOException {

        photo.setImageData(file.getBytes());
        photo.setImageUrl(file.getOriginalFilename());
        return repository.save(photo);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
    
}
