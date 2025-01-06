package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Likee;
import br.edu.ifpb.pweb2.flashg.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likee, LikeId> {

    List<Likee> findByPhotoId(Long photoId);

    List<Likee> findByPhotographerId(Long photographerId);
}
