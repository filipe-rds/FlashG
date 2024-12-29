package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Follow;
import br.edu.ifpb.pweb2.flashg.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    // Busca pelos fotógrafos seguidos
    List<Follow> findByFollowedId(Long followedId);

    // Busca pelos fotógrafos seguidores
    List<Follow> findByFollowerId(Long followerId);

}
