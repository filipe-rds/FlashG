package br.edu.ifpb.pweb2.flashg.repository;


import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {

    Optional<Photographer> findByEmail(String email);

    List<Photographer> findByName (String name);

}