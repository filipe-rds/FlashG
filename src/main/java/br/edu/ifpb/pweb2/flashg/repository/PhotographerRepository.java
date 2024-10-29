package br.edu.ifpb.pweb2.flashg.repository;


import br.edu.ifpb.pweb2.flashg.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Integer> {
    public Photographer findByEmail(String email);

}