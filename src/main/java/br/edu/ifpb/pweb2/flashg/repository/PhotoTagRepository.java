package br.edu.ifpb.pweb2.flashg.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.flashg.entity.PhotoTag;
import br.edu.ifpb.pweb2.flashg.entity.PhotoTagId;


@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, PhotoTagId> {

    
    List<PhotoTag> findByPhotoId(Long photoId);
    
    List<PhotoTag> findByTagId(Long tagId);
    
}
