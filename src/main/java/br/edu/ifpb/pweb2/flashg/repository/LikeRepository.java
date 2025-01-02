package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Like;
import br.edu.ifpb.pweb2.flashg.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    List<Like> findByPhotoId(Long photoId);

    List<Like> findByPhotographerId(Long photographerId);
}
