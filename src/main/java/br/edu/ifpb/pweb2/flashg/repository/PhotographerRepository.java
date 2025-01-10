package br.edu.ifpb.pweb2.flashg.repository;


import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
    List<Photographer> findByFirstName(String firstName);
    List<Photographer> findByLastName(String lastName);
    Optional<Photographer> findByEmail(String email);
    Optional<Photographer> findByUsername(String username);
    List<Photographer> findByUsernameStartingWith(String prefix);
    @Query("SELECT f.followed FROM Follow f WHERE f.follower.id = :id")
    List<Photographer> findAllFollowing(@Param("id") Long id);
}