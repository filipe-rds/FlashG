package br.edu.ifpb.pweb2.flashg.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.pweb2.flashg.entity.Tag;

import java.util.List;
import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Integer> {   

    Optional<Tag> findByTagName(String tagName);

    List<Tag> findByTagNameContainingIgnoreCase(String tagName);
}
