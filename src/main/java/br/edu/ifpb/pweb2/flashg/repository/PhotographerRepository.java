package br.edu.ifpb.pweb2.flashg.repository;


import br.edu.ifpb.pweb2.flashg.entity.Photo;
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
    List<Photographer> findByUsernameIgnoreCaseStartingWith(String prefix);
    @Query("SELECT f.followed FROM Follow f WHERE f.follower.id = :id")
    List<Photographer> findAllFollowing(@Param("id") Long id);
    @Query("SELECT f.follower FROM Follow f WHERE f.followed.id = :id")
    List<Photographer> findAllFollowers(@Param("id") Long id);
    @Query("SELECT p.isBlocked FROM Photographer p WHERE p.id = :id")
    Boolean isPhotographerBlocked(@Param("id") Long id);
    List<Photographer> findAllByOrderByIdAsc();
    @Query("SELECT p.photos FROM Photographer p WHERE p.id = :id")
    List<Photo> findAllPhotos(@Param("id") Long id);
}