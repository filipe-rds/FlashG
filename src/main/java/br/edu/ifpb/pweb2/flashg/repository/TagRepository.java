package br.edu.ifpb.pweb2.flashg.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.pweb2.flashg.entity.Tag;


public interface TagRepository extends JpaRepository<Tag, Integer> {   
    
}
