package br.edu.ifpb.pweb2.flashg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.pweb2.flashg.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
