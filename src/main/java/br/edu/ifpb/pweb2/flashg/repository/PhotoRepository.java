package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.flashg.entity.Likee;
import br.edu.ifpb.pweb2.flashg.entity.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {


    @Query("SELECT P FROM Photo P WHERE P.photographer.id = :id")
    public List<Photo> getPhotosByPhotographerId(Long id);


}
