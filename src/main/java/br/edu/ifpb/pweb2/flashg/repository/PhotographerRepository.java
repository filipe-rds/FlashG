package br.edu.ifpb.pweb2.flashg.repository;


import br.edu.ifpb.pweb2.flashg.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {
    Photographer findByEmail(String email);

    List<Photographer> findByName (String name);

}