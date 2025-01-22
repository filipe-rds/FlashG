package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Likee;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likee, LikeId> {

    List<Likee> findByPhotoId(Long photoId);

    List<Likee> findByPhotographerId(Long photographerId);

    @Query("SELECT l FROM Likee l WHERE l.photo.id = :idPhoto AND l.photographer.id = :idPhotographer")
    Optional<Likee> findLikeByPhotoAndPhotographer(@Param("idPhoto") Long photoid, @Param("idPhotographer") Long photographerid);

    @Query("SELECT COUNT(l) FROM Likee l WHERE l.photo.id = :id")
    public Integer getLikeCount(Long id);

    //@Query("SELECT l FROM Likee l WHERE l.photographerId.id = :id")
    //public List<Likee> getLikesByPhotographerId(Long id);
    
}
